package com.putri.fauziah.usamapapp.network;

import com.putri.fauziah.usamapapp.model.ImagesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ImagesService {
    @GET("search/photos")
    Call<ImagesResponse> searchPhotos(
            @Query("client_id") String clientId,
            @Query("query") String query,
            @Query("per_page") int perPage
    );

}
