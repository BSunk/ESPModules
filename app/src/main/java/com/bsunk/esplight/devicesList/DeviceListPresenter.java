package com.bsunk.esplight.devicesList;


import com.bsunk.esplight.data.model.AllResponse;
import com.bsunk.esplight.data.model.LightModel;
import com.bsunk.esplight.data.rest.ApiInterface;
import com.bsunk.esplight.data.rest.DeviceAccess;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.json.JSONObject;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by Bharat on 2/17/2017.
 */

public class DeviceListPresenter implements DeviceListContract.Presenter {

    private DeviceListContract.View mView;
    private Realm realm;
    private RealmResults<LightModel> devices;
    private CompositeDisposable disposables;

    DeviceListPresenter(DeviceListContract.View view) {
        mView = view;
        disposables = new CompositeDisposable();
        realm = Realm.getDefaultInstance();
    }

    public void getDevices() {
        RealmQuery<LightModel> query = realm.where(LightModel.class);
        devices = query.findAll();
        mView.showDevices(devices);
        if(!devices.isEmpty()) {
            updateData();
        }
    }

    public void onDestroy() {
        realm.close();
        disposables.clear();
    }

    public void updateData() {
        for(int i=0; i<devices.size(); i++) {
            updateDevice(devices.get(i).getIp(), devices.get(i).getPort(), devices.get(i).getChipID());
        }
    }

    private void updateDevice(String ipAddress, String port, final String chipID) {
        ApiInterface apiInterface = new Retrofit.Builder()
                .baseUrl("http://" + ipAddress + ":" + port)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiInterface.class);

        Observable<AllResponse> observable = apiInterface.requestAllData()
                .repeatWhen(completed -> completed.delay(8, TimeUnit.SECONDS));

        disposables.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<AllResponse>() {
                    @Override
                    public void onNext(AllResponse result) {
                        updateDeviceOnRealm(result, chipID);
                    }
                    @Override
                    public void onError(Throwable e) {
                        updateDeviceOnRealmConnectionFailed(chipID);
                    }
                    @Override
                    public void onComplete() {}
                }));
    }

    private void updateDeviceOnRealm(final AllResponse result, final String chipID) {
        realm.executeTransactionAsync(realm -> {
            LightModel updateObject = realm.where(LightModel.class).equalTo("chipID", chipID).findFirst();
            updateObject.setConnectionCheck(true);
            if(result.getPower()==0) {
                updateObject.setPower(false);
            }
            else {
                updateObject.setPower(true);
            }
            updateObject.setBrightness(result.getBrightness());
            updateObject.setPattern(result.getCurrentPattern().getIndex());
            updateObject.setPatternList(new Gson().toJson(result.getPatterns()));
            updateObject.setSolidColorR(result.getSolidColor().getR());
            updateObject.setSolidColorG(result.getSolidColor().getG());
            updateObject.setSolidColorB(result.getSolidColor().getB());
            realm.insertOrUpdate(updateObject);
        });
    }

    private void updateDeviceOnRealmConnectionFailed(final String chipID) {
        realm.executeTransactionAsync(realm -> {
            LightModel updateObject = realm.where(LightModel.class).equalTo("chipID", chipID).findFirst();
            updateObject.setConnectionCheck(false);
            realm.insertOrUpdate(updateObject);
        });
    }

    public void setBrightness(final String ip, final String port, final int brightness, final String chipID) {
        disposables.add(DeviceAccess.getInstance().getSetBrightnessObservable(ip, port, brightness)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String result) {
                        realm.executeTransactionAsync(realm -> {
                            LightModel updateObject = realm.where(LightModel.class).equalTo("chipID", chipID).findFirst();
                            if(!result.equals("")) {
                                updateObject.setBrightness(Integer.parseInt(result));
                                realm.insertOrUpdate(updateObject);
                            }
                        });
                    }
                    @Override
                    public void onError(Throwable throwable) {}
                    @Override
                    public void onComplete() {}
                }));
    }

    public void setPower(final String ip, final String port, final int value, final String chipID) {
        disposables.add(DeviceAccess.getInstance().getSetPowerObservable(ip, port, value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(final String result) {
                        realm.executeTransactionAsync(realm -> {
                            LightModel updateObject = realm.where(LightModel.class).equalTo("chipID", chipID).findFirst();
                            if(result.equals("1")) {
                                updateObject.setPower(true);
                            }
                            else {updateObject.setPower(false);}
                            realm.insertOrUpdate(updateObject);
                        });
                    }
                    @Override
                    public void onError(Throwable throwable) {}
                    @Override
                    public void onComplete() {}
                }));
    }

    public void setPattern(final String ip, final String port, final int value, final String chipID) {
        disposables.add(DeviceAccess.getInstance().getSetPatternObservable(ip, port, value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(final String result) {
                        realm.executeTransactionAsync(realm -> {
                            LightModel updateObject = realm.where(LightModel.class).equalTo("chipID", chipID).findFirst();

                            try {
                                JSONObject obj = new JSONObject(result);
                                updateObject.setPattern(obj.getInt("index"));
                                realm.insertOrUpdate(updateObject);
                            } catch (Throwable t) {
                                Timber.v("Error parsing json string");
                            }
                        });
                    }
                    @Override
                    public void onError(Throwable throwable) {}
                    @Override
                    public void onComplete() {}
                }));
    }

    public void setSolidColor(final String ip, final String port, final int r, final int g, final int b, final String chipID) {
        disposables.add(DeviceAccess.getInstance().getSetSolidColorObservable(ip, port, r, g, b)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(final String result) {
                        realm.executeTransactionAsync(realm -> {
                            LightModel updateObject = realm.where(LightModel.class).equalTo("chipID", chipID).findFirst();

                            try {
                                JSONObject obj = new JSONObject(result);
                                updateObject.setSolidColorR(obj.getInt("r"));
                                updateObject.setSolidColorG(obj.getInt("g"));
                                updateObject.setSolidColorB(obj.getInt("b"));
                                realm.insertOrUpdate(updateObject);
                            } catch (Throwable t) {
                                Timber.v("Error parsing json string");
                            }
                        });
                    }
                    @Override
                    public void onError(Throwable throwable) {}
                    @Override
                    public void onComplete() {}
                }));
    }


}
