package com.example.login_page;

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
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.loginButton);

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