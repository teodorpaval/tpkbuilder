package com.example.teo.tpkbuilder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private MonsterDatabase monsterDatabase;
    //testing
    private Monster monster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        monsterDatabase = MonsterDatabase.getDatabase(getApplicationContext());

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
                startActivity(addIntent);
            }
        });

    }
}
