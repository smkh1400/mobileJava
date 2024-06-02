package com.example.cpu_xo;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.lang.reflect.Array;
import java.security.cert.TrustAnchor;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    ArrayList<ArrayList<View>> tiles = new ArrayList<>();

    ArrayList<ArrayList<Integer>> board = new ArrayList<>();
    Button reset;

    Button back;

    Drawable cross;
    Drawable zero;

    TextView currentTurn;
    Boolean ended;

    int turn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent menuIntent = getIntent();
        String gameType = menuIntent.getStringExtra("type");
        String symbol = menuIntent.getStringExtra("symbol");

        ended = false;
        cross = ResourcesCompat.getDrawable(getResources(), R.drawable.cross, null);
        zero = ResourcesCompat.getDrawable(getResources(), R.drawable.zero, null);


        currentTurn = (TextView) findViewById(R.id.TV_turn);

        if (symbol.equals("X")) {
            currentTurn.setText("Turn: X");
            turn = 0;
        } else {
            currentTurn.setText(("Turn: O"));
            turn = 1;
        }

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

                if (symbol.equals("X")) {
                    currentTurn.setText("Turn: X");
                    turn = 0;
                } else {
                    currentTurn.setText(("Turn: O"));
                    turn = 1;
                }

            }
        });

        back = (Button) findViewById(R.id.BT_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuIntent = new Intent(GameActivity.this, MenuActivity.class);
                startActivity(menuIntent);
                GameActivity.this.finish();
            }
        });


        if (gameType.equals("Human")) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int finalI = i;
                    int finalJ = j;
                    tiles.get(i).get(j).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (turn == 0 && !ended) {
                                if (board.get(finalI).get(finalJ) == -1) {
                                    tiles.get(finalI).get(finalJ).setBackground(cross);
                                    board.get(finalI).set(finalJ, 0);
                                    turn = 1;
                                    currentTurn.setText("Turn: O");
                                    check(board, currentTurn);
                                }
                            } else if (turn == 1 && !ended) {
                                if (board.get(finalI).get(finalJ) == -1) {
                                    tiles.get(finalI).get(finalJ).setBackground(zero);
                                    board.get(finalI).set(finalJ, 1);
                                    turn = 0;
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
        } else if (gameType.equals("CPU")) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int finalI = i;
                    int finalJ = j;
                    tiles.get(i).get(j).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            boolean changed = false;
                            if (turn == 0 && !ended) {
                                if (board.get(finalI).get(finalJ) == -1) {
                                    tiles.get(finalI).get(finalJ).setBackground(cross);
                                    board.get(finalI).set(finalJ, 0);
                                    turn = 1;
                                    currentTurn.setText("Turn: O");
                                    check(board, currentTurn);
                                    changed = true;
                                }
                            } else if (turn == 1 && !ended) {
                                if (board.get(finalI).get(finalJ) == -1) {
                                    tiles.get(finalI).get(finalJ).setBackground(zero);
                                    board.get(finalI).set(finalJ, 1);
                                    turn = 0;
                                    currentTurn.setText("Turn: X");
                                    check(board, currentTurn);
                                    changed = true;
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

                            if (!ended && changed) {
                                nextMoveForCPU(board, (turn));
                                turn = 1 - turn;
                                if (turn == 0)
                                    currentTurn.setText("Turn: X");
                                else
                                    currentTurn.setText("Turn: O");
                                check(board, currentTurn);
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


    }

    private void nextMoveForCPU(ArrayList<ArrayList<Integer>> board, int turn) {
        Drawable[] symbols = new Drawable[]{cross, zero};
        boolean special = false;
        int[] nextTile = new int[2];
        if ((board.get(1).get(1) == turn && board.get(0).get(0) == (1 - turn) && board.get(2).get(2) == (1 - turn))
                || (board.get(1).get(1) == turn && board.get(0).get(2) == (1 - turn) && board.get(2).get(0) == (1 - turn))) {
            special = true;
        }
        if (board.get(1).get(1) == -1) { // if center is empty CPU fills it
            tiles.get(1).get(1).setBackground(symbols[turn]);
            board.get(1).set(1, turn);
        } else if ((nextTile = checkForWin(board, turn)) != null) { // then check whether it is in danger or not
            tiles.get(nextTile[0]).get(nextTile[1]).setBackground(symbols[turn]);
            board.get(nextTile[0]).set(nextTile[1], turn);
        } else if ((nextTile = checkForDanger(board, turn)) != null) { // then check whether it can win
            tiles.get(nextTile[0]).get(nextTile[1]).setBackground(symbols[turn]);
            board.get(nextTile[0]).set(nextTile[1], turn);
        } else if ((nextTile = checkForEmptyCorners(board)) != null && !special) { // finally check if there is any corner left
            tiles.get(nextTile[0]).get(nextTile[1]).setBackground(symbols[turn]);
            board.get(nextTile[0]).set(nextTile[1], turn);
        } else { // the first empty tile
            nextTile = checkForEmptyEdges(board);
            tiles.get(nextTile[0]).get(nextTile[1]).setBackground(symbols[turn]);
            board.get(nextTile[0]).set(nextTile[1], turn);
        }
    }

    private int[] checkForEmptyEdges(ArrayList<ArrayList<Integer>> board) {
        if (board.get(0).get(1) == -1)
            return new int[]{0, 1};
        if (board.get(1).get(0) == -1)
            return new int[]{1, 0};
        if (board.get(1).get(2) == -1)
            return new int[]{1, 2};
        if (board.get(2).get(1) == -1)
            return new int[]{2, 1};
        return null;
    }

    private int[] checkForEmptyCorners(ArrayList<ArrayList<Integer>> board) {
        if (board.get(0).get(0) == -1)
            return new int[]{0, 0};
        if (board.get(0).get(2) == -1)
            return new int[]{0, 2};
        if (board.get(2).get(0) == -1)
            return new int[]{2, 0};
        if (board.get(2).get(2) == -1)
            return new int[]{2, 2};
        return null;
    }

    private int[] checkForDanger(ArrayList<ArrayList<Integer>> board, int turn) {
        int[] nextTile = new int[2];
        // check rows
        for (int i = 0; i < 3; i++) {
            int countEnemy = 0;
            int countEmpty = 0;
            int emptyPlace = 0;
            for (int j = 0; j < 3; j++) {
                if (board.get(i).get(j) == -1) {
                    countEmpty += 1;
                    emptyPlace = j;
                } else if (board.get(i).get(j) == (1 - turn)) {
                    countEnemy += 1;
                }
            }
            if (countEnemy == 2 && countEmpty == 1) {
                nextTile[0] = i;
                nextTile[1] = emptyPlace;
                return nextTile;
            }
        }
        // check columns
        for (int j = 0; j < 3; j++) {
            int countEnemy = 0;
            int countEmpty = 0;
            int emptyPlace = 0;
            for (int i = 0; i < 3; i++) {
                if (board.get(i).get(j) == -1) {
                    countEmpty += 1;
                    emptyPlace = i;
                } else if (board.get(i).get(j) == (1 - turn)) {
                    countEnemy += 1;
                }
            }
            if (countEnemy == 2 && countEmpty == 1) {
                nextTile[0] = emptyPlace;
                nextTile[1] = j;
                return nextTile;
            }
        }
        // check diameters
        int countEnemy = 0;
        int countEmpty = 0;
        int emptyPlace = 0;
        for (int i = 0; i < 3; i++) {
            if (board.get(i).get(i) == -1) {
                countEmpty += 1;
                emptyPlace = i;
            } else if (board.get(i).get(i) == (1 - turn)) {
                countEnemy += 1;
            }
        }
        if (countEnemy == 2 && countEmpty == 1) {
            nextTile[0] = emptyPlace;
            nextTile[1] = emptyPlace;
            return nextTile;
        }

        countEnemy = 0;
        countEmpty = 0;
        emptyPlace = 0;
        for (int i = 0; i < 3; i++) {
            if (board.get(i).get(2 - i) == -1) {
                countEmpty += 1;
                emptyPlace = i;
            } else if (board.get(i).get(2 - i) == (1 - turn)) {
                countEnemy += 1;
            }
        }
        if (countEnemy == 2 && countEmpty == 1) {
            nextTile[0] = emptyPlace;
            nextTile[1] = 2 - emptyPlace;
            return nextTile;
        }

        return null;
    }

    private int[] checkForWin(ArrayList<ArrayList<Integer>> board, int turn) {
        int[] nextTile = new int[2];
        // check rows
        for (int i = 0; i < 3; i++) {
            int countMine = 0;
            int countEmpty = 0;
            int emptyPlace = 0;
            for (int j = 0; j < 3; j++) {
                if (board.get(i).get(j) == -1) {
                    countEmpty += 1;
                    emptyPlace = j;
                } else if (board.get(i).get(j) == turn) {
                    countMine += 1;
                }
            }
            if (countMine == 2 && countEmpty == 1) {
                nextTile[0] = i;
                nextTile[1] = emptyPlace;
                return nextTile;
            }
        }
        // check columns
        for (int j = 0; j < 3; j++) {
            int countMine = 0;
            int countEmpty = 0;
            int emptyPlace = 0;
            for (int i = 0; i < 3; i++) {
                if (board.get(i).get(j) == -1) {
                    countEmpty += 1;
                    emptyPlace = i;
                } else if (board.get(i).get(j) == turn) {
                    countMine += 1;
                }
            }
            if (countMine == 2 && countEmpty == 1) {
                nextTile[0] = emptyPlace;
                nextTile[1] = j;
                return nextTile;
            }
        }
        // check diameters
        int countMine = 0;
        int countEmpty = 0;
        int emptyPlace = 0;
        for (int i = 0; i < 3; i++) {
            if (board.get(i).get(i) == -1) {
                countEmpty += 1;
                emptyPlace = i;
            } else if (board.get(i).get(i) == turn) {
                countMine += 1;
            }
        }
        if (countMine == 2 && countEmpty == 1) {
            nextTile[0] = emptyPlace;
            nextTile[1] = emptyPlace;
            return nextTile;
        }

        countMine = 0;
        countEmpty = 0;
        emptyPlace = 0;
        for (int i = 0; i < 3; i++) {
            if (board.get(i).get(2 - i) == -1) {
                countEmpty += 1;
                emptyPlace = i;
            } else if (board.get(i).get(2 - i) == (1 - turn)) {
                countMine += 1;
            }
        }
        if (countMine == 2 && countEmpty == 1) {
            nextTile[0] = emptyPlace;
            nextTile[1] = 2 - emptyPlace;
            return nextTile;
        }

        return null;
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