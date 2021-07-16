package com.example.apilistdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    static String api_endPoint = "https://followchess.com/config.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        //Show progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Fetching api data");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        //Get Data
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();
                    Request request = new Request.Builder()
                            .url(api_endPoint)
                            .method("GET", null)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            call.cancel();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            if(progressDialog.isShowing())
                                progressDialog.dismiss();

                            final String resp = response.body().string();

                            Log.i("Resp", resp);
                            try {
                                JSONObject obj = new JSONObject(resp);
                                JSONArray arr = obj.getJSONArray("trns");

                                POJOItems[] POJOItems = new POJOItems[arr.length()];

                                for(int i=0; i<arr.length(); i++) {
                                    JSONObject obj1 = arr.getJSONObject(i);

                                    String name = getValue(obj1, "name");
                                    String slug = getValue(obj1, "slug");
                                    String img = getValue(obj1, "img");

                                    String trimSlug = slug.substring(0, slug.length()-5);
                                    String slugYear = slug.substring(slug.length()-4, slug.length());
                                    String dashCount = String.valueOf(countDash(slug));

                                    POJOItems[i] = new POJOItems(name, trimSlug, slugYear, dashCount, img);
                                }
                                setRecyclerData(POJOItems);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }

    }


    protected void setRecyclerData(final POJOItems[] POJOItems)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                    AdapterItems adapter = new AdapterItems(POJOItems, getApplicationContext(), MainActivity.this);
                    recyclerView.setNestedScrollingEnabled(false);
                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    static protected String getValue(JSONObject obj, String name) {
        String str = "";
        try {
            String s = obj.getString(name);
            str = s;
        } catch (Exception e) {
        }
        return str;
    }

    static int countDash(String str)
    {
        int count = 0;
        for(int i=0; i<str.length(); i++)
        {
            if(str.substring(i, i+1).equals("-"))
                count++;
        }
        return count;
    }
}