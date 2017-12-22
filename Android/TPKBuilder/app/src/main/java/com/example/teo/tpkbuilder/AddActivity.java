package com.example.teo.tpkbuilder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final EditText textName = (EditText) findViewById(R.id.textName);
        final EditText textCR = (EditText) findViewById(R.id.textCR);

        Button btnAdd = (Button) findViewById(R.id.buttonCreate);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MonsterDatabase.getDatabase(getApplicationContext())
                        .monsterDao().insert(new Monster(textName.getText().toString(),
                                        Integer.parseInt(textCR.getText().toString())));
                finish();
            }
        });
    }
}
