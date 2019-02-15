package com.example.ashleyshorter.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Event {

    double voteAverage;
    int eventId;
    String posterPath;
    String title;
    String overview;
    String backdropPath;

    public Event() {
    }

    public Event(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        backdropPath = jsonObject.getString("backdrop_path");
        voteAverage = jsonObject.getDouble("vote_average");
        eventId = jsonObject.getInt("id");
    }

    public static List<Event> fromJsonArray(JSONArray eventjsonArray) throws JSONException {
        List<Event> events = new ArrayList<>();
        for (int i = 0; i < eventjsonArray.length(); i++) {
            events.add(new Event(eventjsonArray.getJSONObject(i)));
        }
        return events;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);

    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getEventId() {
        return eventId;
    }
}
