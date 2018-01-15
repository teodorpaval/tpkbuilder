package com.example.teo.tpkbuilder;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    public static MonsterController monsterController = new MonsterController();
    ArrayList<String> monsterDataJson;
    ArrayAdapter<Monster> monsterArrayAdapter;
    ArrayAdapter<String> stringArrayAdapter;
    MonsterDatabase monsterDatabase;
    static String monsterUrl = "http://10.0.2.2:3000/monsters";
    Monster monster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);



        //-------------------DATABASE STUFF-------------------

        monsterDatabase = MonsterDatabase.getDatabase(getApplicationContext());
        monsterArrayAdapter = new ArrayAdapter<Monster>(
                this,
                android.R.layout.simple_list_item_1,
                monsterDatabase.monsterDao().getEntries());

        stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, monsterDataJson);

        ListView monsterView = (ListView) findViewById(R.id.listView);



        monsterView.setAdapter(monsterArrayAdapter);
        Log.v("ListActivity", "aaaaaaaaaaaaaaaaa");
        final Intent editIntent = new Intent(this, EditActivity.class);

        monsterView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editIntent.putExtra("index", i);
                startActivity(editIntent);
                //monsterArrayAdapter.notifyDataSetChanged();
            }
        });


        Log.v("ListActivity", "bbbbbbbbbbbbbbb");
    }


    @Override
    protected void onResume() {
        super.onResume();

        monsterArrayAdapter.clear();
        monsterArrayAdapter.addAll(MonsterDatabase.getDatabase(getApplicationContext())
                .monsterDao().getEntries());
        monsterArrayAdapter.notifyDataSetChanged();
        
        Log.d("ListActivity", "resumed");
    }
}
