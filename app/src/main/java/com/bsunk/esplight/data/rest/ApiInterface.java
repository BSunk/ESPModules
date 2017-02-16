package com.bsunk.esplight.data.rest;

import com.bsunk.esplight.data.model.AllResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Bharat on 2/15/2017.
 */

public interface ApiInterface {

    @GET("all")
    Observable<AllResponse> requestAllData();

}
