package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RadioGroup gender;
    RadioButton radioButton;
    Button register;
    EditText fullName;
    EditText username;
    EditText password;
    EditText phone;
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        fullName = (EditText) findViewById(R.id.fullName);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
        register = (Button) findViewById(R.id.register);
        gender = (RadioGroup) findViewById(R.id.gender);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = gender.getCheckedRadioButtonId();

                radioButton = (RadioButton) findViewById(selected);
                HashMap<String, String> data = new HashMap<>();
                data.put("fullName", fullName.getText().toString());
                data.put("gender", radioButton.getText().toString());
                data.put("username", username.getText().toString());
                data.put("password", password.getText().toString());
                data.put("phone", phone.getText().toString());
                data.put("email", email.getText().toString());
                register(data);
            }
        });

    }

    private void register(HashMap<String, String> data) {
        String text = "fullname: " + data.get("fullName") + "\n" +
                "gender: "  + data.get("gender") + "\n" +
                "username: " + data.get("username") + "\n" +
                "password: " + data.get("password") + "\n" +
                "phone: " + data.get("phone") + "\n" +
                "email: " + data.get("email") + "\n";
        Toast.makeText(getBaseContext(), text, Toast.LENGTH_LONG).show();

    }
}