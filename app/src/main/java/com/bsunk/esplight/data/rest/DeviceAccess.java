package com.bsunk.esplight.data.rest;

import com.bsunk.esplight.data.components.DaggerNetComponent;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Observable;
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

    public DeviceAccess() {
        DaggerNetComponent.builder().build().inject(this);
    }

//    public String setBrightness(String ip, String port, int brightness) {
//
//        String url = "http://"+ip+":"+port+"/brightness?value="+brightness;
//        String json = "";
//
//        try {
//            RequestBody body = RequestBody.create(JSON, json);
//            Request request = new Request.Builder()
//                    .url(url)
//                    .post(body)
//                    .build();
//            Response response = okHttpClient.newCall(request).execute();
//            return response.body().string();
//        }
//        catch(IOException e) {
//            return "";
//        }
//    }

    public Observable<Void> setBrightness(String ip, String port, int brightness) {



    }

    public static DeviceAccess getInstance() {
        if(instance==null) {
            instance = new DeviceAccess();
        }
        return instance;
    }

}
