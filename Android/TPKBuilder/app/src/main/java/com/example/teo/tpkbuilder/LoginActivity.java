package com.example.teo.tpkbuilder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import org.json.JSONArray;

public class LoginActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameText = (EditText) findViewById(R.id.loginUsernameText);
        final EditText passwordText = (EditText) findViewById(R.id.loginPasswordText);

        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        if(getIntent().getBooleanExtra("LoginFailed", false))
        {
            builder.setMessage("Invalid Username or Password")
                    .setTitle("Error");

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        Button btnLogin = (Button) findViewById(R.id.loginButton);
        final Intent loadingIntent = new Intent(this, LoadingActivity.class);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingIntent.putExtra("username", usernameText.getText().toString());
                loadingIntent.putExtra("password", passwordText.getText().toString());

                startActivity(loadingIntent);
            }
        });
    }
}
