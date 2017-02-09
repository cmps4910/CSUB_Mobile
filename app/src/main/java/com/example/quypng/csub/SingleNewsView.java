package com.example.quypng.csub;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SingleNewsView extends Activity {
    // Declare Variables
//	String rank;
//	String country;
//	String population;
//	String flag;
//	String position;
//	ImageLoader imageLoader = new ImageLoader(this);

    TextView singletextview;
    TextView date;
    ProgressDialog mProgressDialog;

    // Get link and store in url string
    String csubSite = "http://www.csub.edu/news/news_archives/";
    String title;
    String link;
    String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.news_singleview_constraint);

        Intent i = getIntent();
        // Get the result of rank
        title = i.getStringExtra("title");
        // Get the result of country
        link = i.getStringExtra("link");
        //link = link.replaceAll("\\s+","%20");
        url = csubSite + link;

        TextView txtTitle = (TextView) findViewById(R.id.title);
//        TextView txtLink = (TextView) findViewById(R.id.link);
        txtTitle.setText(title);
//        txtLink.setText(url);

        // Execute DownloadJSON AsyncTask
        new JsoupListView().execute();
    }

    // Title AsyncTask
    private class JsoupListView extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(SingleNewsView.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Android Jsoup ListView Tutorial");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        String newsContent = "";
        String articleDate;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the Website URL
                Document doc = Jsoup.connect(url).get();

                for (Element div : doc.select("div[class=article_text]")) {

                    for (Element row : div.select("p")) {
                        newsContent += row.text();
                    }
                    newsContent = div.text();
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
            // Locate the listview in listview_main.xml
            singletextview = (TextView) findViewById(R.id.contentView);
            singletextview.setText(newsContent);

            date = (TextView) findViewById(R.id.link);
            date.setText(articleDate);

            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }
}