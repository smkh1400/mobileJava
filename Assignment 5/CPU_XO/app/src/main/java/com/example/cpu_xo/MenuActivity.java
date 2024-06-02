package com.example.cpu_xo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    TextView welcome;

    RadioGroup gameType;

    RadioGroup XO;

    Button startGame;

    RadioButton type;

    RadioButton symbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_page);

        Intent previousActivity = getIntent();
        String username = previousActivity.getStringExtra("username");

        welcome = (TextView) findViewById(R.id.welcome);
        welcome.setText("welcome " + username);
        gameType = (RadioGroup) findViewById(R.id.RG_cpu_human);
        XO = (RadioGroup) findViewById(R.id.RG_X_O);
        startGame = (Button) findViewById(R.id.BT_start_game);

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedTypeID = gameType.getCheckedRadioButtonId();
                int selectedXOID = XO.getCheckedRadioButtonId();

                type = (RadioButton) findViewById(selectedTypeID);
                symbol = (RadioButton) findViewById(selectedXOID);


                Intent gameIntent = new Intent(MenuActivity.this, GameActivity.class);
                gameIntent.putExtra("type", type.getText().toString());
                gameIntent.putExtra("symbol", symbol.getText().toString());
                startActivity(gameIntent);
                MenuActivity.this.finish();

            }
        });

    }
}