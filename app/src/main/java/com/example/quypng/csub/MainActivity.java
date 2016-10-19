package com.example.quypng.csub;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);

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
}
