package com.putri.fauziah.usamapapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class State implements Parcelable {
    @SerializedName("ID State")
    private String id;

    @SerializedName("State")
    private String state;

    @SerializedName("ID Year")
    private int idYear;

    @SerializedName("Year")
    private String year;

    @SerializedName("Population")
    private int population;

    @SerializedName("Slug State")
    private String slug;

    // Add the imageUrl field
    private String imageUrl;

    public State() {
        // Default constructor
    }

    // Constructor to create from Parcel
    protected State(Parcel in) {
        id = in.readString();
        state = in.readString();
        idYear = in.readInt();
        year = in.readString();
        population = in.readInt();
        slug = in.readString();
        imageUrl = in.readString(); // Read imageUrl from Parcel
    }

    // Parcelable.Creator
    public static final Creator<State> CREATOR = new Creator<State>() {
        @Override
        public State createFromParcel(Parcel in) {
            return new State(in);
        }

        @Override
        public State[] newArray(int size) {
            return new State[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(state);
        dest.writeInt(idYear);
        dest.writeString(year);
        dest.writeInt(population);
        dest.writeString(slug);
        dest.writeString(imageUrl); // Write imageUrl to Parcel
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getIdYear() {
        return idYear;
    }

    public void setIdYear(int idYear) {
        this.idYear = idYear;
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

    // Getter and setter for imageUrl
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
