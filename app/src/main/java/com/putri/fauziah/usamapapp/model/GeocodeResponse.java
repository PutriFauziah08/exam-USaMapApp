package com.putri.fauziah.usamapapp.model;

import com.google.gson.annotations.SerializedName;

public class GeocodeResponse {
    @SerializedName("results")
    private Results[] results;

    public Results[] getResults() {
        return results;
    }

    public static class Results {
        @SerializedName("geometry")
        private Geometry geometry;

        public Geometry getGeometry() {
            return geometry;
        }

        public static class Geometry {
            @SerializedName("lat")
            private double lat;

            @SerializedName("lng")
            private double lng;

            public double getLat() {
                return lat;
            }

            public double getLng() {
                return lng;
            }
        }
    }
}
