package com.putri.fauziah.usamapapp.network;

import com.google.gson.JsonObject;
import com.putri.fauziah.usamapapp.model.DataNationResponse;
import com.putri.fauziah.usamapapp.model.DataResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("api/data?")
    Call<DataResponse> getStateList(
            @Query("drilldowns") String drilldowns,
            @Query("measures") String measures,
            @Query("year") String year
    );

    @GET("api/data?")
    Call<DataNationResponse> getNationList(
            @Query("drilldowns") String drilldowns,
            @Query("measures") String measures,
            @Query("year") String year
    );

    @GET("api/data?")
    Call<JsonObject> getMedAgeList(
            @Query("drilldowns") String drilldowns,
            @Query("measures") String measures,
            @Query("geo") String geo,
            @Query("year") String year
    );

}
