package com.app.csubmobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;


public class SingleNewsView extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;

    TextView newsTitle;
    TextView newsContent;
    TextView newsDate;
    ProgressDialog mProgressDialog;

    String csubSite = "http://www.csub.edu/news/news_archives/";
    String title;
    String link;
    String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_singleview_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        title = i.getStringExtra("title");
        link = i.getStringExtra("link");
        //link = link.replaceAll("\\s+","%20");
        url = csubSite + link;

        TextView txtTitle = (TextView) findViewById(R.id.title);
        txtTitle.setText(title);

        // Execute DownloadJSON AsyncTask
        new JsoupListView().execute();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    //    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menuRight) {
            if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                drawer.closeDrawer(Gravity.RIGHT);
            } else {
                drawer.openDrawer(Gravity.RIGHT);
            }
            return true;
        }
        if (id == R.id.home) {
//            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_map) {
            // Launching Map
            Intent i = new Intent(getApplicationContext(), MapActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_news) {
            // Launching News
            Intent i = new Intent(getApplicationContext(), NewsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_events) {
            // Launching Events
            Intent i = new Intent(getApplicationContext(), EventsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_directory) {
            // Launching Directory
            Intent i = new Intent(getApplicationContext(), DirectoryActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_socialmedia) {
            // Launching Social Media
            Intent i = new Intent(getApplicationContext(), SocialMediaActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_dining) {
            // Launching Dining
            Intent i = new Intent(getApplicationContext(), DiningActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_transportation) {
            // Launching Transportation
            Intent i = new Intent(getApplicationContext(), TransportationActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_schedule) {
            // Launching Schedule
            Intent i = new Intent(getApplicationContext(), ScheduleActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_blackboard) {
            // Launching Blackboard/Moodle
            Intent i = new Intent(getApplicationContext(), Blackboard_MoodleActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    // Title AsyncTask
    private class JsoupListView extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(SingleNewsView.this);
            mProgressDialog.setTitle("Retrieving news content...\"");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        String articleContent = "";
        String articleDate = "";
        String articleTitle = "";

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the Website URL
                Document doc = Jsoup.connect(url).get();
                for (Element div : doc.select("div[class=article_text]")) {
                    for (Element header1 : div.select("h1")) {
                        articleTitle = header1.text();
                    }
                    // check if div element has its own text
                    // if is not empty, fetch the content else
                    // fetch the content in <p>
                    if (!div.ownText().isEmpty()) {
                        articleContent += div.ownText();
                    } else {
                        for (Element para : div.select("p")) {
                            articleContent += para.text() + "\n\n";
                        }
                    }
                }
                for (Element div : doc.select("div[class=articleDate]")) {
                    articleDate = div.text();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            newsContent = (TextView) findViewById(R.id.contentView);
            newsContent.setText(articleContent);
            newsDate = (TextView) findViewById(R.id.newsDate);
            newsDate.setText(articleDate);
            mProgressDialog.dismiss();
        }
    }
}