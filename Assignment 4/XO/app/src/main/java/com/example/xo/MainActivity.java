package com.example.xo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ArrayList<ArrayList<View>> tiles = new ArrayList<>();

    Button reset;

    Drawable cross;
    Drawable zero;
    
    TextView currentTurn;
    Boolean ended;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ended = false;
        cross = ResourcesCompat.getDrawable(getResources(), R.drawable.cross, null);
        zero = ResourcesCompat.getDrawable(getResources(), R.drawable.zero, null);


        currentTurn = (TextView) findViewById(R.id.TV_turn);

        Random rand = new Random();

        final int[] turn = {rand.nextInt(2)};

        if (turn[0] == 0) {
            currentTurn.setText("Turn: X");
        } else {
            currentTurn.setText(("Turn: O"));
        }

        ArrayList<ArrayList<Integer>> board = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            row.add(-1);
            row.add(-1);
            row.add(-1);
            board.add(row);
        }

        ArrayList<View> tileRow0 = new ArrayList<>();
        tileRow0.add((View) findViewById(R.id.x_1));
        tileRow0.add((View) findViewById(R.id.x_2));
        tileRow0.add((View) findViewById(R.id.x_3));
        ArrayList<View> tileRow1 = new ArrayList<>();
        tileRow1.add((View) findViewById(R.id.x_4));
        tileRow1.add((View) findViewById(R.id.x_5));
        tileRow1.add((View) findViewById(R.id.x_6));
        ArrayList<View> tileRow2 = new ArrayList<>();
        tileRow2.add((View) findViewById(R.id.x_7));
        tileRow2.add((View) findViewById(R.id.x_8));
        tileRow2.add((View) findViewById(R.id.x_9));
        tiles.add(tileRow0);
        tiles.add(tileRow1);
        tiles.add(tileRow2);

        reset = (Button) findViewById(R.id.BT_reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ended = false;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        tiles.get(i).get(j).setBackground(null);
                        board.get(i).set(j, -1);
                    }
                }
                turn[0] = rand.nextInt(2);

                if (turn[0] == 0) {
                    currentTurn.setText("Turn: X");
                } else {
                    currentTurn.setText(("Turn: O"));
                }

            }
        });

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int finalI = i;
                int finalJ = j;
                tiles.get(i).get(j).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (turn[0] == 0 && !ended) {
                            if (board.get(finalI).get(finalJ) == -1) {
                                tiles.get(finalI).get(finalJ).setBackground(cross);
                                board.get(finalI).set(finalJ, 0);
                                turn[0] = 1;
                                currentTurn.setText("Turn: O");
                                check(board, currentTurn);
                            }
                        } else if (turn[0] == 1 && !ended) {
                            if (board.get(finalI).get(finalJ) == -1) {
                                tiles.get(finalI).get(finalJ).setBackground(zero);
                                board.get(finalI).set(finalJ, 1);
                                turn[0] = 0;
                                currentTurn.setText("Turn: X");
                                check(board, currentTurn);
                            }
                        }
                        if (ended == false) {
                            Boolean full = true;
                            for (int k = 0; k < 3; k++) {
                                for (int l = 0; l < 3; l++) {
                                    if (board.get(k).get(l) == -1) {
                                        full = false;
                                        break;
                                    }
                                }
                            }
                            if (full) {
                                currentTurn.setText("Draw");
                                ended = true;
                            }
                        }
                    }
                });
            }
        }
        
        
    }

    private void check(ArrayList<ArrayList<Integer>> board, TextView text) {
        ended = true;
        if (board.get(0).get(0) != -1 &&  board.get(0).get(0) == board.get(0).get(1) && board.get(0).get(1) == board.get(0).get(2)) {
            if (board.get(0).get(0) == 1) {
                text.setText("player O wins!");
            } else {
                text.setText("player X wins!");
            }
        } else if (board.get(1).get(0) != -1 &&  board.get(1).get(0) == board.get(1).get(1) && board.get(1).get(1) == board.get(1).get(2)) {
            if (board.get(1).get(0) == 1) {
                text.setText("player O wins!");
            } else {
                text.setText("player X wins!");
            }
        } else if (board.get(2).get(0) != -1 &&  board.get(2).get(0) == board.get(2).get(1) && board.get(2).get(1) == board.get(2).get(2)) {
            if (board.get(2).get(0) == 1) {
                text.setText("player O wins!");
            } else {
                text.setText("player X wins!");
            }
        } else if (board.get(0).get(0) != -1 &&  board.get(0).get(0) == board.get(1).get(0) && board.get(1).get(0) == board.get(2).get(0)) {
            if (board.get(0).get(0) == 1) {
                text.setText("player O wins!");
            } else {
                text.setText("player X wins!");
            }
        } else if (board.get(0).get(1) != -1 &&  board.get(0).get(1) == board.get(1).get(1) && board.get(1).get(1) == board.get(2).get(1)) {
            if (board.get(0).get(1) == 1) {
                text.setText("player O wins!");
            } else {
                text.setText("player X wins!");
            }
        } else if (board.get(0).get(2) != -1 &&  board.get(0).get(2) == board.get(1).get(2) && board.get(1).get(2) == board.get(2).get(2)) {
            if (board.get(0).get(2) == 1) {
                text.setText("player O wins!");
            } else {
                text.setText("player X wins!");
            }
        } else if (board.get(0).get(0) != -1 &&  board.get(0).get(0) == board.get(1).get(1) && board.get(1).get(1) == board.get(2).get(2)) {
            if (board.get(0).get(0) == 1) {
                text.setText("player O wins!");
            } else {
                text.setText("player X wins!");
            }
        } else if (board.get(0).get(2) != -1 &&  board.get(0).get(2) == board.get(1).get(1) && board.get(1).get(1) == board.get(2).get(0)) {
            if (board.get(0).get(2) == 1) {
                text.setText("player O wins!");
            } else {
                text.setText("player X wins!");
            }
        } else {
            ended = false;
        }
    }
}