package com.putri.fauziah.usamapapp.model;


import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Images {
    @SerializedName("urls")
    private PhotoUrls urls;

    public PhotoUrls getUrls() {
        return urls;
    }

    public void setUrls(PhotoUrls urls) {
        this.urls = urls;
    }
}
