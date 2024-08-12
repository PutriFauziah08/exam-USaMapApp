package com.putri.fauziah.usamapapp.network;

import com.putri.fauziah.usamapapp.model.GeocodeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MapsService {
    @GET("geocode/v1/json")
    Call<GeocodeResponse> getGeocode(
            @Query("q") String query,
            @Query("key") String apiKey,
            @Query("language") String language,
            @Query("pretty") int pretty
    );
}
