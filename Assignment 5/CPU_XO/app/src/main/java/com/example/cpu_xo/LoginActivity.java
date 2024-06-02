package com.example.cpu_xo;

import android.content.Intent;
import android.os.Bundle;
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

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("ali") && password.getText().toString().equals("12345678")) {
                    Toast.makeText(getBaseContext(), "login successful: welcome " + username.getText().toString(), Toast.LENGTH_LONG).show();
                    Intent menuIntent = new Intent(LoginActivity.this, MenuActivity.class);
                    menuIntent.putExtra("username", username.getText().toString());
                    startActivity(menuIntent);
                    LoginActivity.this.finish();
                } else {
                    Toast.makeText(getBaseContext(), "login failed", Toast.LENGTH_LONG).show();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                intent.putExtra("username", "ali");
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });

    }
}