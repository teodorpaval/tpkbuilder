package com.example.teo.tpkbuilder;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddActivity extends AppCompatActivity {
    private MonsterDatabase monsterDatabase;
    String baseUrl = "http://192.168.1.5:3000/";
    String url;
    RequestQueue requestQueue;
    Boolean isLoggedIn = false;

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public void addMonster(int id, String name, String cr)
    {
        monsterDatabase = MonsterDatabase.getDatabase(getApplicationContext());
        this.url = this.baseUrl + "monsters";
        String jsonBody = "{\"id\":" + Integer.toString(id) + ",\"name\":\"" + name + "\",\"cr\":" + cr + "}";
        Log.v("dumnezocumila", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + jsonBody);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(jsonBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.v("URL", "------------------------------------" + url);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.POST, url,jsonObject,
                new Response.Listener<JSONArray>(){

                    @Override
                    public void onResponse(JSONArray response) {
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", "AddNaspa");
                    }
                }
        );
        if (isNetworkConnected())
            requestQueue.add(arrayRequest);
        else
            MainActivity.requests.add(arrayRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        requestQueue = Volley.newRequestQueue(this);

        final EditText textName = (EditText) findViewById(R.id.textName);
        final EditText textCR = (EditText) findViewById(R.id.textCR);

        Button btnAdd = (Button) findViewById(R.id.buttonCreate);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MonsterDatabase.getDatabase(getApplicationContext())
                        .monsterDao().insert(new Monster(textName.getText().toString(),
                                        Integer.parseInt(textCR.getText().toString())));
                addMonster(0, textName.getText().toString(), textCR.getText().toString());
                finish();
            }
        });
    }
}
