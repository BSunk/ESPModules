package com.bsunk.esplight.addModuleConfirm;

import com.bsunk.esplight.data.model.AllResponse;
import com.bsunk.esplight.data.model.LightModel;
import com.bsunk.esplight.data.model.mDNSModule;
import com.bsunk.esplight.data.rest.ApiInterface;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Bharat on 12/24/2016.
 */

public class AddModulesConfirmPresenter implements AddModulesConfirmContract.Presenter {

    AddModulesConfirmContract.View mView;
    private CompositeDisposable disposables;
    Realm realm;

    @Inject
    AddModulesConfirmPresenter(AddModulesConfirmContract.View view) {
        mView = view;
        disposables = new CompositeDisposable();
        realm = Realm.getDefaultInstance();
    }

    public void onItemClickModule(mDNSModule module) {
        mView.setValues(module);
    }

    public void testConnection(String ipAddress, int port) {
        ApiInterface apiInterface = new Retrofit.Builder()
                .baseUrl("http://" + ipAddress + ":" + port)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiInterface.class);

        Observable<AllResponse> observable = apiInterface.requestAllData();
        mView.showProgressBar(true);

        disposables.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(onClickTestObserver()));
    }

    public void saveConnection(final LightModel lightModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(lightModel);
            }
        });
        mView.saveComplete();

    }

    private DisposableObserver<AllResponse> onClickTestObserver() {
        return new DisposableObserver<AllResponse>() {
            @Override
            public void onNext(AllResponse result) {
                mView.showValidation(true);
                mView.showProgressBar(false);
            }

            @Override
            public void onError(Throwable e) {
                mView.showValidation(false);
                mView.showProgressBar(false);
            }

            @Override
            public void onComplete() {

            }
        };
    }

    public void onStop() {
        disposables.dispose();
        realm.close();
    }

}
