package com.example.quypng.csub;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.quypng.csub.NewsActivity;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    ImageLoader imageLoader;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public ListViewAdapter(Context context,
                           ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
//		imageLoader = new ImageLoader(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        // Declare Variables
        TextView title;
        TextView link;
//		TextView rank;
//		TextView country;
//		TextView population;
//		ImageView flag;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.news_listview_item, parent, false);
        // Get the position
        resultp = data.get(position);

        // Locate the TextViews in listview_item.xml
        title = (TextView) itemView.findViewById(R.id.title);
        link = (TextView) itemView.findViewById(R.id.link);
//		rank = (TextView) itemView.findViewById(R.id.rank);
//		country = (TextView) itemView.findViewById(R.id.country);
//		population = (TextView) itemView.findViewById(R.id.population);

        // Locate the ImageView in listview_item.xml
//		flag = (ImageView) itemView.findViewById(R.id.flag);

        // Capture position and set results to the TextViews
        title.setText(resultp.get(NewsActivity.TITLE));
        link.setText(resultp.get(NewsActivity.LINK));
//		rank.setText(resultp.get(MainActivity.RANK));
//		country.setText(resultp.get(MainActivity.COUNTRY));
//		population.setText(resultp.get(MainActivity.POPULATION));
        // Capture position and set results to the ImageView
        // Passes flag images URL into ImageLoader.class
//		imageLoader.DisplayImage(resultp.get(MainActivity.FLAG), flag);
        // Capture ListView item click
        itemView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get the position
                resultp = data.get(position);
                Intent intent = new Intent(context, SingleNewsView.class);
                // Pass all data rank
                intent.putExtra("title", resultp.get(NewsActivity.TITLE));
                // Pass all data country
                intent.putExtra("link", resultp.get(NewsActivity.LINK));
//				// Pass all data population
//				intent.putExtra("population",resultp.get(MainActivity.POPULATION));
//				// Pass all data flag
//				intent.putExtra("flag", resultp.get(MainActivity.FLAG));
                // Start SingleNewsView Class
                context.startActivity(intent);

            }
        });
        return itemView;
    }
}
