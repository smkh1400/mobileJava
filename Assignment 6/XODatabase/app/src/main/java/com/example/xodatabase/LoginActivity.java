package com.example.xodatabase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText username;

    EditText password;

    Button loginButton;

    TextView signup;

    Button deleteGameStates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        Intent signupActivity = getIntent();
        String savedUsername = signupActivity.getStringExtra("username");
        String savedPassword = signupActivity.getStringExtra("password");

        username = (EditText) findViewById(R.id.username);
        username.setText(savedUsername);
        password = (EditText) findViewById(R.id.password);
        password.setText(savedPassword);
        loginButton = (Button) findViewById(R.id.loginButton);
        signup = (TextView) findViewById(R.id.signup);
        deleteGameStates = (Button) findViewById(R.id.deleteGameStates);

        deleteGameStates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameStateDatabaseHandler db = new GameStateDatabaseHandler(LoginActivity.this);
                db.deleteAll();
                db.close();
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UsersDatabaseHandler db = new UsersDatabaseHandler(LoginActivity.this);
                Users user = db.getUser(username.getText().toString(), password.getText().toString());
                if (user != null) {
                    Toast.makeText(getBaseContext(), "login successful: welcome " + username.getText().toString(), Toast.LENGTH_LONG).show();
                    Intent menuIntent = new Intent(LoginActivity.this, MenuActivity.class);
                    menuIntent.putExtra("nickname", user.get_nickname());
                    menuIntent.putExtra("username", user.get_username());
                    startActivity(menuIntent);
                    LoginActivity.this.finish();
                } else {
                    Toast.makeText(getBaseContext(), "username or password incorrect", Toast.LENGTH_LONG).show();
                }
                db.close();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });

    }
}