package com.putri.fauziah.usamapapp.model;

import com.google.gson.annotations.SerializedName;

public class PhotoUrls {
    @SerializedName("regular")
    private String regular;

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }
}