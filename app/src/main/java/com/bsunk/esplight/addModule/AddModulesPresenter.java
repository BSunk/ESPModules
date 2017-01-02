package com.bsunk.esplight.addModule;

import com.bsunk.esplight.data.model.mDNSModule;
import com.bsunk.esplight.helper.NsdHelper;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Bharat on 12/16/2016.
 */

public class AddModulesPresenter implements AddModulesContract.Presenter, NsdHelper.OnModuleAddCallback {

    private AddModulesContract.View mView;
    private NsdHelper nsdHelper;
    private static final int TIMEOUT = 5;
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    AddModulesPresenter(NsdHelper nsdHelper, AddModulesContract.View view) {
        mView = view;
        this.nsdHelper = nsdHelper;
        mView.initializeList();
        nsdHelper.initializeNsd();
        nsdHelper.registerCallback(this);
    }

    public void startDiscoveryTimeOut() {
        if(!nsdHelper.isDiscovery) {
            mView.resetList();
            mView.showEmptyMessage(false);
            mView.startSearchAnimation(true);
            nsdHelper.discoverServices();

            Observable<Long> observable = Observable.timer(TIMEOUT, TimeUnit.SECONDS, Schedulers.io());

            disposables.add(observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<Long>() {
                        @Override
                        public void onNext(Long value) {
                            nsdHelper.stopDiscovery();
                            mView.startSearchAnimation(false);
                            mView.showEmptyMessage(true);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    }));
        }
    }

    @Override
    public void OnDiscovered(final mDNSModule module) {

        Single.fromCallable(new Callable<Void>() {
            @Override
            public Void call() {
                mView.addModuleToList(module);
                return null;
            }
        })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Void>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onSuccess(Void value) {}

                    @Override
                    public void onError(Throwable error) {}
                });
    }

    @Override
    public void OnRemoved() {
        nsdHelper.stopDiscovery();

        Single.fromCallable(new Callable<Void>() {
            @Override
            public Void call() {
                mView.resetList();
                return null;
            }
        })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Void>() {
                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onSuccess(Void value) {
                        nsdHelper.discoverServices();
                    }

                    @Override
                    public void onError(Throwable error) {}
                });
    }

    public void onResume() {
        if (nsdHelper != null) {
            startDiscoveryTimeOut();
        }
    }
    public void onPause() {
        if (nsdHelper != null) {
            nsdHelper.stopDiscovery();
        }
        disposables.clear();
    }

}
