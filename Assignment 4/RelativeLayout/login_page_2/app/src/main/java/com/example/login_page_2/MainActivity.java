package com.example.login_page_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username;

    EditText password;

    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page_2);

        username = (EditText) findViewById(R.id.ET_username);
        password = (EditText) findViewById(R.id.ET_password);
        loginButton = (Button) findViewById(R.id.BT_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("ali") && password.getText().toString().equals("12345678")) {
                    Toast.makeText(getBaseContext(), "login successful: welcome " + username.getText().toString(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getBaseContext(), "login failed", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}