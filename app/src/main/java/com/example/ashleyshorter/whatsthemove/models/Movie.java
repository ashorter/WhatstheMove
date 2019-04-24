package com.example.ashleyshorter.whatsthemove.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    private final String name;
    private final String description;
    private final String price;
    private final String category;
    private final String imageName;

    int movieId;

    public Movie(String name, String description, String price, String category,
                String imageName) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imageName = imageName;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getImageName() {
        return imageName;
    }

    public int getMovieId() {
        return movieId;
    }
}