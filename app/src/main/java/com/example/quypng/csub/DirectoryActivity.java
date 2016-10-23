package com.example.quypng.csub;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class DirectoryActivity extends Activity {
    private ProgressDialog pDialog;
    public static List<String> directory = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        get_data(URL_FACULTY,directory);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directory_layout);
        if (get_data(URL_FACULTY,directory))
            setList();
    }

    private void setList() {
        ListView listView = (ListView) findViewById(R.id.directory);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, directory);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged ();
    }

    private boolean get_data(String url, final List list) {
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
                        String fullname = new String();
                        if (title != null)
                            fullname = title + " " + json.getString("faculty_fname") + " " + json.getString("faculty_lname");
                        else
                            fullname = json.getString("faculty_fname") + " " + json.getString("faculty_lname");
                        name[i] = fullname;
                    }
                    if (list.isEmpty()) {
                        for (int i = 0; i < name.length; i++) {
                            list.add(name[i]);
                        }
                    }
                    adapter.notifyDataSetChanged ();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                Toast.makeText(DirectoryActivity.this, "Failure getting data from server", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(strReq);
        return true;
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
