package com.putri.fauziah.usamapapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class ImagesResponse {
    @SerializedName("results")
    private List<Images> results;

    public List<Images> getResults() {
        return results;
    }

    public void setResults(List<Images> results) {
        this.results = results;
    }
}

