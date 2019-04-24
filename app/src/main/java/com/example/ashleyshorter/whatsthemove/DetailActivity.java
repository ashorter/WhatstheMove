package com.example.ashleyshorter.whatsthemove;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.ashleyshorter.whatsthemove.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class DetailActivity extends YouTubeBaseActivity {

    public static final String YOUTUBE_API_KEY = "AIzaSyBZgL6maMjU4dHlJF7cGBheMP8_I4Ul7Fw";
    public static final String TRAILERS_API = "https://www.eventbriteapi.com/v3/events/55869374804/?token=T5JRKOVJGIYHNNF4OC6O";

    TextView tvTitle;
    TextView tvOverview;
    //RatingBar ratingBar;
    //YouTubePlayerView youTubePlayerView;

    Movie movie;

    Button btnNotAtten;
    Button btnAtten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvOverview);
        //ratingBar = findViewById(R.id.ratingBar);
        //youTubePlayerView = findViewById(R.id.player);

        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        tvTitle.setText(movie.getName());
        tvOverview.setText(movie.getDescription());
        //ratingBar.setRating((float) movie.getVoteAverage());

        btnNotAtten = (Button)findViewById(R.id.btnNotAtten);
        btnAtten = (Button)findViewById(R.id.btnAtten);

        btnNotAtten.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MovieActivity.class);
                startActivity(i);
            }
        });

        btnAtten.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),RatingActivity.class);
                startActivity(i);
            }
        });

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(TRAILERS_API, movie.getMovieId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray results = response.getJSONArray("name");
                    if (results.length() == 0){
                        return;
                    }
                    JSONObject movieTrailer = results.getJSONObject(0);
                    String youtubeKey = movieTrailer.getString("key");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }

        });

    }
}