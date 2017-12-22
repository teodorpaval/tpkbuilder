package com.example.teo.tpkbuilder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class EditActivity extends AppCompatActivity {
    //public static MonsterController monsterController = ListActivity.monsterController;
    //public MonsterDatabase monsterDatabase = MonsterDatabase.getDatabase(getApplicationContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        //String nameString = getIntent().getStringExtra("name");
        Log.v("EditActivity", "start");
        final int index = getIntent().getIntExtra("index", -1);
        final EditText name = (EditText) findViewById(R.id.fldName);
        final MonsterDatabase monsterDatabase = MonsterDatabase.getDatabase(getApplicationContext());
        final Monster currentMonster = monsterDatabase.monsterDao().getEntries().get(index);
        //final Monster currentMonster = new Monster();
        name.setText(currentMonster.getName());

        Button saveButton = (Button) findViewById(R.id.buttonSave);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentMonster.setName(name.getText().toString());
                monsterDatabase.monsterDao().update(currentMonster);
                finish();
            }
        });

        Button deleteButton = (Button) findViewById(R.id.buttonDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monsterDatabase.monsterDao().remove(currentMonster);
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
