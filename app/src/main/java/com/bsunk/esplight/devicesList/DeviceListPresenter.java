package com.bsunk.esplight.devicesList;


import com.bsunk.esplight.data.model.AllResponse;
import com.bsunk.esplight.data.model.LightModel;
import com.bsunk.esplight.data.rest.ApiInterface;
import com.bsunk.esplight.data.rest.DeviceAccess;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.Callable;

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

    @Inject
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
        disposables.dispose();
    }

    private void updateData() {
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

        Observable<AllResponse> observable = apiInterface.requestAllData();

        disposables.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<AllResponse>() {
                    @Override
                    public void onNext(AllResponse result) {
                        updateRealm(result, chipID);
                    }
                    @Override
                    public void onError(Throwable e) {
                        updateRealmDeviceError(chipID);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    private void updateRealm(final AllResponse result, final String chipID) {
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
                updateObject.setPattern(result.getCurrentPattern().getName());
                realm.insertOrUpdate(updateObject);
            }, () ->{mView.updatedData();});
    }

    private void updateRealmDeviceError(final String chipID) {
        realm.executeTransactionAsync(realm -> {
                LightModel updateObject = realm.where(LightModel.class).equalTo("chipID", chipID).findFirst();
                updateObject.setConnectionCheck(false);
                realm.insertOrUpdate(updateObject);
            }, () -> {mView.updatedData();});
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
                            updateObject.setBrightness(Integer.parseInt(result));
                            realm.insertOrUpdate(updateObject);
                        }, () -> {mView.updatedData();});
                    }
                    @Override
                    public void onError(Throwable throwable) {}
                    @Override
                    public void onComplete() {}
                }));
    }


}
