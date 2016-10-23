package com.example.quypng.csub;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.quypng.csub.EndpointsActivity.URL_FACULTY;

public class MainActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    public static List<String> directory = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        get_data(URL_FACULTY,directory);
        /**
         * Creating all buttons instances
         * */
        // Dashboard News button
        Button btn_newsfeed = (Button) findViewById(R.id.btn_news);

        // Dashboard Map button
        Button btn_friends = (Button) findViewById(R.id.btn_map);

        // Dashboard Directory button
        Button btn_messages = (Button) findViewById(R.id.btn_dictory);

        // Dashboard Dining button
        Button btn_places = (Button) findViewById(R.id.btn_dining);

        // Dashboard Events button
        Button btn_events = (Button) findViewById(R.id.btn_events);

        // Dashboard Social Media button
        Button btn_photos = (Button) findViewById(R.id.btn_socialmedia);

        /**
         * Handling all button click events
         * */

        // Listening to News Feed button click
        btn_newsfeed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching News Feed Screen
                Intent i = new Intent(getApplicationContext(), NewsActivity.class);
                startActivity(i);
            }
        });

        // Listening Map button click
        btn_friends.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching Map Feed Screen
                Intent i = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(i);
            }
        });

        // Listening Directory button click
        btn_messages.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching Directory Screen
                Intent i = new Intent(getApplicationContext(), DirectoryActivity.class);
                startActivity(i);
            }
        });

        // Listening to Dining button click
        btn_places.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching Dining Screen
                Intent i = new Intent(getApplicationContext(), DiningActivity.class);
                startActivity(i);
            }
        });

        // Listening to Events button click
        btn_events.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching Events Screen
                Intent i = new Intent(getApplicationContext(), EventsActivity.class);
                startActivity(i);
            }
        });

        // Listening to Social Media button click
        btn_photos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching Social Media Screen
                Intent i = new Intent(getApplicationContext(), SocialMediaActivity.class);
                startActivity(i);
            }
        });

    }

    private void get_data(String url, final List list) {
        pDialog.setMessage("Loading data ...");
        showDialog();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hideDialog();
                try {
                    JSONArray jObj = new JSONArray(response);
                    JSONObject json= null;
                    final String[] name = new String[jObj.length()];
                    for(int i=0;i<jObj.length(); i++){
                        json = jObj.getJSONObject(i);
                        String title = (json.isNull("faculty_title")) ? null : json.getString("faculty_title");
                        String fullname = title + " " + json.getString("faculty_fname") + " " + json.getString("faculty_lname");
                        name[i] = fullname;
                    }
                    if (list.isEmpty()) {
                        for (int i = 0; i < name.length; i++) {
                            list.add(name[i]);
                        }
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                Toast.makeText(MainActivity.this, "Failure getting data from server", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(strReq);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
