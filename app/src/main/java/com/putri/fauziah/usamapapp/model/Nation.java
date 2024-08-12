package com.putri.fauziah.usamapapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Nation implements Parcelable {
    @SerializedName("ID Nation")
    private String id;

    @SerializedName("Nation")
    private String nation;

    @SerializedName("ID Year")
    private int idYear;

    @SerializedName("Year")
    private String year;

    @SerializedName("Population")
    private int population;

    @SerializedName("Slug Nation")
    private String slug;

    public Nation() {
        // Default constructor
    }

    // Constructor to create from Parcel
    protected Nation(Parcel in) {
        id = in.readString();
        nation = in.readString();
        idYear = in.readInt();
        year = in.readString();
        population = in.readInt();
        slug = in.readString();
    }

    // Parcelable.Creator
    public static final Creator<Nation> CREATOR = new Creator<Nation>() {
        @Override
        public Nation createFromParcel(Parcel in) {
            return new Nation(in);
        }

        @Override
        public Nation[] newArray(int size) {
            return new Nation[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nation);
        dest.writeInt(idYear);
        dest.writeString(year);
        dest.writeInt(population);
        dest.writeString(slug);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getter and setter methods
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        nation = nation;
    }

    public int getIdYear() {
        return idYear;
    }

    public void setIdYear(int idyear) {
        this.idYear = idyear;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
