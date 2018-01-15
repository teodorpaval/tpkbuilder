package com.example.teo.tpkbuilder;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EditActivity extends AppCompatActivity {
    //public static MonsterController monsterController = ListActivity.monsterController;
    //public MonsterDatabase monsterDatabase = MonsterDatabase.getDatabase(getApplicationContext());
    private MonsterDatabase monsterDatabase;
    String baseUrl = "http://192.168.1.5:3000/";
    String url;
    RequestQueue requestQueue;

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public void deleteMonster(int id)
    {
        monsterDatabase = MonsterDatabase.getDatabase(getApplicationContext());
        this.url = this.baseUrl + "monsters/" + Integer.toString(id);
        Log.v("URL", "------------------------------------" + url);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.DELETE, url,
                new Response.Listener<JSONArray>(){

                    @Override
                    public void onResponse(JSONArray response) {
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
        if (isNetworkConnected())
            requestQueue.add(arrayRequest);
        else
            MainActivity.requests.add(arrayRequest);
    }

    public void editMonster(int id, String name, String cr)
    {
        monsterDatabase = MonsterDatabase.getDatabase(getApplicationContext());
        this.url = this.baseUrl + "monsters/" + Integer.toString(id);
        String jsonBody = "{\"id\":" + Integer.toString(id) + ",\"name\":\"" + name + "\",\"cr\":" + cr + "}";
        Log.v("dumnezocumila", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + jsonBody);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(jsonBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.v("URL", "------------------------------------" + url);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.PUT, url,jsonObject,
                new Response.Listener<JSONArray>(){

                    @Override
                    public void onResponse(JSONArray response) {
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", "EditNaspa");
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
        setContentView(R.layout.activity_edit);
        requestQueue = Volley.newRequestQueue(this);

        //String nameString = getIntent().getStringExtra("name");
        Log.v("EditActivity", "start");
        final int index = getIntent().getIntExtra("index", -1);
        final EditText name = (EditText) findViewById(R.id.fldName);
        final EditText cr = (EditText) findViewById(R.id.editCrText);
        final MonsterDatabase monsterDatabase = MonsterDatabase.getDatabase(getApplicationContext());
        final Monster currentMonster = monsterDatabase.monsterDao().getEntries().get(index);
        //final Monster currentMonster = new Monster();
        name.setText(currentMonster.getName());
        cr.setText(Integer.toString(currentMonster.getCr()));

        Button saveButton = (Button) findViewById(R.id.buttonSave);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentMonster.setName(name.getText().toString());
                currentMonster.setCr(Integer.parseInt(cr.getText().toString()));
                monsterDatabase.monsterDao().update(currentMonster);
                //int cr = currentMonster.getCr();
                editMonster(index + 1, currentMonster.getName(), Integer.toString(currentMonster.getCr()));
                finish();
            }
        });

        final Button deleteButton = (Button) findViewById(R.id.buttonDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monsterDatabase.monsterDao().remove(currentMonster);
                deleteMonster(index + 1);
                finish();
            }
        });

        Button shareButton = (Button) findViewById(R.id.buttonShare);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, "teopaval@gmail.com");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Monsters");
                intent.putExtra(Intent.EXTRA_TEXT, name.getText());

                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

    }


}
