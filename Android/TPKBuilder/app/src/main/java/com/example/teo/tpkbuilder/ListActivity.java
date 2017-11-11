package com.example.teo.tpkbuilder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListActivity extends AppCompatActivity {
    public static MonsterController monsterController = new MonsterController();
    ArrayAdapter<Monster> listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView monsterView = (ListView) findViewById(R.id.listView);

        listAdapter = new ArrayAdapter<Monster>(
                this,
                android.R.layout.simple_list_item_1,
                monsterController.getMonsterArrayList());

        monsterView.setAdapter(listAdapter);
        Log.v("ListActivity", "aaaaaaaaaaaaaaaaa");
        final Intent editIntent = new Intent(this, EditActivity.class);

        monsterView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editIntent.putExtra("index", i);
                startActivity(editIntent);
                //listAdapter.notifyDataSetChanged();
            }
        });
        Log.v("ListActivity", "bbbbbbbbbbbbbbb");
    }


    @Override
    protected void onResume() {
        super.onResume();
        listAdapter.notifyDataSetChanged();
    }
}
