package com.example.teo.tpkbuilder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoadingActivity extends AppCompatActivity {
    String baseUrl = "http://192.168.1.5:3000/";
    String url;
    RequestQueue requestQueue;

    private void authenticateUser(String username, String password, final LoadingActivity loadingActivity)
    {
        //monsterDatabase = MonsterDatabase.getDatabase(getApplicationContext());
        this.url = this.baseUrl + "users/?username=" + username + "&password=" + password;
        Log.v("URL", "------------------------------------" + url);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>(){

                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() > 0)
                        {
                            JSONObject jsonObject = new JSONObject();
                            Intent mainIntent = new Intent(loadingActivity, MainActivity.class);
                            try {
                                jsonObject = response.getJSONObject(0);

                                mainIntent.putExtra("rank", jsonObject.getString("rank"));
                                Log.v("Loading", "==============" + jsonObject.getString("rank"));
                                startActivity(mainIntent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else
                        {
                            Intent loginIntent = new Intent(loadingActivity, LoginActivity.class);
                            loginIntent.putExtra("LoginFailed", true);
                            startActivity(loginIntent);
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", "EditNaspa");
                    }
                }
        );
        requestQueue.add(arrayRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        requestQueue = Volley.newRequestQueue(this);

        authenticateUser(getIntent().getStringExtra("username"), getIntent().getStringExtra("password"), this);

    }
}
