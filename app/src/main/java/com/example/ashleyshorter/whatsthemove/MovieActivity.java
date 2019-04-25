package com.example.ashleyshorter.whatsthemove;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ashleyshorter.whatsthemove.adapters.MoviesAdapter;
import com.example.ashleyshorter.whatsthemove.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static java.security.AccessController.getContext;

public class MovieActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private List<Object> mRecyclerViewItems = new ArrayList<>();

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        mRecyclerView = (RecyclerView) findViewById(R.id.rvMovie);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new MoviesAdapter(this, mRecyclerViewItems);
        mRecyclerView.setAdapter(adapter);

        addMenuItemsFromJson();

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.action_search:
                        //fragment = new PostsFragment();
                        break;
                    case R.id.action_favorites:
                        //fragment = new ComposeFragment();
                        break;
                    case R.id.action_profile:
                        ParseUser.logOut();
                        ParseUser currentUser = ParseUser.getCurrentUser();
                        Intent i = new Intent(MovieActivity.this, LoginActivity.class);
                        startActivity(i);
                        Toast.makeText(MovieActivity.this, "Successfully logged out!", Toast.LENGTH_SHORT).show();
                    default:
                        break;
                }
                //fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.action_search);
    }

    private void addMenuItemsFromJson() {
        try {
            String jsonDataString = readJsonDataFromFile();
            JSONArray menuItemsJsonArray = new JSONArray(jsonDataString);

            for (int i = 0; i < menuItemsJsonArray.length(); ++i) {

                JSONObject menuItemObject = menuItemsJsonArray.getJSONObject(i);

                String menuItemName = menuItemObject.getString("name");
                String menuItemDescription = menuItemObject.getString("description");
                String menuItemPrice = menuItemObject.getString("price");
                String menuItemCategory = menuItemObject.getString("category");
                String menuItemImageName = menuItemObject.getString("photo");
                String menuItemTime = menuItemObject.getString("time");
                String menuItemDate = menuItemObject.getString("date");
                String menuItemLocation = menuItemObject.getString("location");
                String menuItemOrganizer = menuItemObject.getString("organizer");
                String menuPosterPath = menuItemObject.getString("photo");

                Movie movie = new Movie(menuItemName, menuItemDescription, menuItemPrice,
                        menuItemCategory, menuItemImageName, menuItemTime, menuItemLocation, menuItemOrganizer, menuPosterPath); //String time, String venue, String organizer
                mRecyclerViewItems.add(movie);
            }
        } catch (IOException | JSONException exception) {
            Log.e(MovieActivity.class.getName(), "Unable to parse JSON file.", exception);
        }
    }

    private String readJsonDataFromFile() throws IOException {

        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();

        try {
            String jsonDataString = null;
            inputStream = getResources().openRawResource(R.raw.menu_item);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            while ((jsonDataString = bufferedReader.readLine()) != null) {
                builder.append(jsonDataString);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return new String(builder);
    }
}
