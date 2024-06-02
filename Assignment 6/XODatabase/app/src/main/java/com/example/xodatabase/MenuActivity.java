package com.example.xodatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    TextView welcome;

    RadioGroup gameType;

    RadioGroup XO;

    Button newGame;

    RadioButton type;

    RadioButton symbol;

    Button showGameHistory;

    Button deleteHistory;

    Button resumeGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_page);

        Intent previousActivity = getIntent();
        String nickname = previousActivity.getStringExtra("nickname");
        String username = previousActivity.getStringExtra("username");

        welcome = (TextView) findViewById(R.id.welcome);
        welcome.setText("welcome " + nickname);
        gameType = (RadioGroup) findViewById(R.id.RG_Computer_human);
        XO = (RadioGroup) findViewById(R.id.RG_X_O);
        newGame = (Button) findViewById(R.id.BT_new_game);
        showGameHistory = (Button) findViewById(R.id.BT_show_game_history);
        deleteHistory = (Button) findViewById(R.id.BT_delete_history);
        resumeGame = (Button) findViewById(R.id.BT_resume_game);
        GameStateDatabaseHandler db = new GameStateDatabaseHandler(MenuActivity.this);
        boolean hasUnfinishedGame = db.getNumberOfGameStates(username) > 0 ? true : false;
        if (!hasUnfinishedGame) {
            resumeGame.setVisibility(View.GONE);
        } else {
            resumeGame.setVisibility(View.VISIBLE);
        }
        db.close();


        resumeGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameStateDatabaseHandler db = new GameStateDatabaseHandler(MenuActivity.this);
                GameStates gameState = db.getGameState(username);
                db.close();
                Intent gameIntent = new Intent(MenuActivity.this, GameActivity.class);
                gameIntent.putExtra("username", username);
                gameIntent.putExtra("type", gameState.get_gameType());
                gameIntent.putExtra("symbol", gameState.get_symbol());
                gameIntent.putExtra("board", GameStateDatabaseHandler.boardToString(gameState.get_board()));
                startActivity(gameIntent);
                MenuActivity.this.finish();
            }
        });

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if player starts a new game the saved game would be deleted
                GameStateDatabaseHandler db = new GameStateDatabaseHandler(MenuActivity.this);
                db.deleteGameState(username);
                db.close();


                int selectedTypeID = gameType.getCheckedRadioButtonId();
                int selectedXOID = XO.getCheckedRadioButtonId();

                type = (RadioButton) findViewById(selectedTypeID);
                symbol = (RadioButton) findViewById(selectedXOID);

                String board = "000000000";


                Intent gameIntent = new Intent(MenuActivity.this, GameActivity.class);
                gameIntent.putExtra("username", username);
                gameIntent.putExtra("type", type.getText().toString());
                gameIntent.putExtra("symbol", symbol.getText().toString());
                gameIntent.putExtra("board", board);
                startActivity(gameIntent);
                MenuActivity.this.finish();

            }
        });

        showGameHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResultsDatabaseHandler db = new ResultsDatabaseHandler(MenuActivity.this);
                ArrayList<Results> results = db.showAll(username);
                Intent historyIntent = new Intent(MenuActivity.this, History.class);
                if (results != null) {
                    String[] histories = new String[results.size()];
                    for (int i = 0; i < results.size(); i++) {
                        histories[i] = results.get(i).toString();
                    }
                    historyIntent.putExtra("results", histories);
                } else {
                    String[] histories = null;
                    historyIntent.putExtra("result", histories);
                }
                startActivity(historyIntent);
                db.close();
            }
        });

        deleteHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResultsDatabaseHandler db = new ResultsDatabaseHandler(MenuActivity.this);
                db.deleteAll();
                db.close();
            }
        });



    }
}