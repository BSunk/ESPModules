package com.bsunk.esplight.data.rest;


import com.bsunk.esplight.data.components.DaggerNetComponent;
import com.bsunk.esplight.data.model.LightModel;


import java.io.IOException;

import javax.inject.Inject;
import io.reactivex.Observable;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Bharat on 2/19/2017.
 */

public class DeviceAccess {

    @Inject
    OkHttpClient okHttpClient;

    private static DeviceAccess instance;
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private DeviceAccess() {
        DaggerNetComponent.builder().build().inject(this);
    }

    public Observable<String> getSetBrightnessObservable(String ip, String port, int brightness) {
        return Observable.create(e -> {
            e.onNext(setBrightnessCall(ip, port, brightness));
            e.onComplete();
        });
    }

    public Observable<String> getSetPowerObservable(String ip, String port, int value) {
        return Observable.create(e -> {
            e.onNext(setPowerCall(ip, port, value));
            e.onComplete();
        });
    }

    private String setBrightnessCall(String ip, String port, int brightness) {
        String url = "http://"+ip+":"+port+"/brightness?value="+brightness;
        String json = "";

        try {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        }
        catch(IOException e) {
            return "";
        }
    }

    private String setPowerCall(String ip, String port, int power) {
        String url = "http://"+ip+":"+port+"/power?value="+power;
        String json = "";

        try {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        }
        catch(IOException e) {
            return "";
        }
    }

    public static DeviceAccess getInstance() {
        if(instance==null) {
            instance = new DeviceAccess();
        }
        return instance;
    }

}
