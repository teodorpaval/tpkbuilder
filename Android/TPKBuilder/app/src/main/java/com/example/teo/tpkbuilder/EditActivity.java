package com.example.teo.tpkbuilder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class EditActivity extends AppCompatActivity {
    public static MonsterController monsterController = ListActivity.monsterController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        //String nameString = getIntent().getStringExtra("name");
        final int index = getIntent().getIntExtra("index", -1);
        final EditText name = (EditText) findViewById(R.id.fldName);

        name.setText(monsterController.getMonsterArrayList().get(index).getName());

        Button saveButton = (Button) findViewById(R.id.buttonSave);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monsterController.updateMonster(index, name.getText().toString());

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
