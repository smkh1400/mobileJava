package com.example.xo_game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import java.util.HashMap;
import java.util.UUID;

public class SignupActivity extends AppCompatActivity {
    Button register;
    EditText username;
    EditText password;
    EditText nickname;

    TextView haveAccount;

    TelephonyManager telephonyManager;

    String UUID;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 432: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    read();
                }
                return;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @SuppressLint("HardwareIds")
    void read() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        UUID = telephonyManager.getDeviceSoftwareVersion();
        // to get the deviceID it needs READ_PRIVILEGED_PHONE_STATE and android only grant
        // this permission to system apps so instead I get the softwareVersion
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 432);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        nickname = (EditText) findViewById(R.id.nickname);
        haveAccount = (TextView) findViewById(R.id.haveAccount);
        register = (Button) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, String> data = new HashMap<>();
                data.put("username", username.getText().toString());
                data.put("password", password.getText().toString());
                data.put("nickname", nickname.getText().toString());
                register(data);
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                SignupActivity.this.finish();
            }
        });

        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                intent.putExtra("username", username.getText().toString());
                intent.putExtra("password", password.getText().toString());
                startActivity(intent);
                SignupActivity.this.finish();
            }
        });

    }

    private void register(HashMap<String, String> data) {
        String text = "username: " + data.get("username") + "\n" +
                "password: " + data.get("password") + "\n" +
                "nickname: " + data.get("nickname") + "\n";
        Toast.makeText(getBaseContext(), text, Toast.LENGTH_LONG).show();

    }
}