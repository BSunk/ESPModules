package com.bsunk.esplight.addModuleConfirm;

import com.bsunk.esplight.data.model.AllResponse;
import com.bsunk.esplight.data.model.mDNSModule;
import com.bsunk.esplight.data.rest.ApiInterface;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Bharat on 12/24/2016.
 */

public class AddModulesConfirmPresenter implements AddModulesConfirmContract.Presenter {

    AddModulesConfirmContract.View mView;

    @Inject
    AddModulesConfirmPresenter(AddModulesConfirmContract.View view) {
        mView = view;

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

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getTestObserver());
    }

    private DisposableObserver<AllResponse> getTestObserver() {
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

    }

}
