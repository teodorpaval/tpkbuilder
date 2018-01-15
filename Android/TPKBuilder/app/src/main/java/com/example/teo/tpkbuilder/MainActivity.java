package com.example.teo.tpkbuilder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MonsterDatabase monsterDatabase;
    String baseUrl = "http://192.168.1.5:3000/";
    String url;
    RequestQueue requestQueue;
    Boolean isLoggedIn = false;
    String rank;
    static ArrayList<JsonArrayRequest> requests = new ArrayList<>();

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    private void getMonster()
    {


        this.url = this.baseUrl + "monsters";
        Log.v("URL", "------------------------------------" + url);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>(){

                    @Override
                    public void onResponse(JSONArray response) {
                        // Check the length of our response (to see if the user has any repos)
                        if (response.length() > 0) {
                            monsterDatabase.monsterDao().nukeAll();
                            // The user does have repos, so let's loop through them all.
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    // For each repo, add a new line to our repo list.
                                    JSONObject jsonObj = response.getJSONObject(i);

                                    String name = jsonObj.get("name").toString();
                                    String cr = jsonObj.get("cr").toString();
                                    Log.v("onResponse",name + " " + cr);
                                    monsterDatabase.monsterDao().insert(new Monster(name, Integer.parseInt(cr)));
                                    Log.v("onResponse", "--------------------------------------------------------------------------");
                                    //Log.v("onResponse",name + " " + cr);

                                } catch (JSONException e) {
                                    // If there is an error then output this to the logs.
                                    Log.e("Volley", "Invalid JSON Object.");
                                }

                            }
                        } else {
                            // The user didn't have any repos.
                            //setRepoListText("No repos found.");
                            Log.v("nu", "nu-i bine");
                        }

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.e("ERROR", "NASPA");
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
        setContentView(R.layout.activity_main);
        monsterDatabase = MonsterDatabase.getDatabase(getApplicationContext());
        requestQueue = Volley.newRequestQueue(this);
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        getMonster();

        rank = getIntent().getStringExtra("rank");


        Button listButton = (Button) findViewById(R.id.buttonBrowse);
        final Intent listIntent = new Intent(this, ListActivity.class);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ListActivity myList = new ListActivity();
                startActivity(listIntent);
            }
        });

        Button addButton = (Button) findViewById(R.id.buttonAdd);
        final Intent addIntent = new Intent(this, AddActivity.class);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Main", "~~~~~~~~~~~~~~~~~~~~~~~~~" + rank);
                if(rank != null && rank.equals("admin")){
                    startActivity(addIntent);
                }
                else
                {
                    builder.setMessage("You need to be logged in to an admin account to add")
                            .setTitle("Error");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });

        Button loginButton = (Button) findViewById(R.id.mainLoginButton);
        final Intent loginIntent = new Intent(this, LoginActivity.class);
        loginIntent.putExtra("LoginFailed", false);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkConnected())
                    startActivity(loginIntent);
                else
                {
                    builder.setMessage("You need to be connected to the internet to log in to your account!")
                            .setTitle("Error");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        Button syncButton = (Button) findViewById(R.id.btnSyncData);
        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(requests.size() > 0 && isNetworkConnected())
                {
                    for(JsonArrayRequest r : requests)
                    {
                        requestQueue.add(r);
                    }
                    builder.setMessage("The server data has been synchronized.")
                            .setTitle("App");
                }
                else if(isNetworkConnected())
                {
                    builder.setMessage("The database is already up to date!")
                            .setTitle("Warning");
                }
                else
                {
                    builder.setMessage("You need to connect to the internet to sync your data!")
                            .setTitle("Error");
                }
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {}});
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        rank = getIntent().getStringExtra("rank");

    }
}
