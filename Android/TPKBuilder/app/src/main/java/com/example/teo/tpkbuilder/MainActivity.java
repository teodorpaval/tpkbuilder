package com.example.teo.tpkbuilder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button listButton = (Button) findViewById(R.id.buttonBrowse);
        final Intent listIntent = new Intent(this, ListActivity.class);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ListActivity myList = new ListActivity();
                startActivity(listIntent);
            }
        });
    }
}
