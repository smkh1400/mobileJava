package com.example.xodatabase;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity {
    Handler handler = new Handler();

    ArrayList<ArrayList<View>> tiles = new ArrayList<>();

    ArrayList<ArrayList<Integer>> board = new ArrayList<>();
    Button reset;

    Button back;

    Drawable cross;
    Drawable zero;

    TextView currentTurn;
    Boolean ended;

    int turn;

    String symbol;

    String gameType;
    String username;

    boolean changed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        String[] symbols = new String[]{"X", "O"};

        Intent menuIntent = getIntent();
        username = menuIntent.getStringExtra("username");
        gameType = menuIntent.getStringExtra("type");
        symbol = menuIntent.getStringExtra("symbol");
        board = GameStateDatabaseHandler.stringToBoard(menuIntent.getStringExtra("board"));


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

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.get(i).get(j) == 1)
                    tiles.get(i).get(j).setBackground(zero);
                else if (board.get(i).get(j) == 0)
                    tiles.get(i).get(j).setBackground(cross);
            }
        }

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

                if (!ended) {
                    GameStateDatabaseHandler db = new GameStateDatabaseHandler(GameActivity.this);
                    if (db.getNumberOfGameStates(username) == 0) { // if there wasn't any game create it
                        GameStates gameState;
                        gameState = new GameStates();
                        gameState.set_username(username);
                        gameState.set_gameType(gameType);
                        gameState.set_symbol(symbols[turn]);
                        gameState.set_board(board);
                        db.addGameState(gameState);
                    }
                    else { // if there was a saved game update it
                        GameStates gameState = db.getGameState(username);
                        gameState.set_gameType(gameType);
                        gameState.set_symbol(symbols[turn]);
                        gameState.set_board(board);
                        db.updateGameState(gameState);
                        Log.w("board", GameStateDatabaseHandler.boardToString(gameState.get_board()));
                        Log.w("debug", "update");
                    }
                    db.close();

                } else {
                    GameStateDatabaseHandler db = new GameStateDatabaseHandler(GameActivity.this);
                    db.deleteGameState(username);
                    db.close();
                }

                Intent menuIntent = new Intent(GameActivity.this, MenuActivity.class);
                menuIntent.putExtra("username", username);
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
                                    ResultsDatabaseHandler db = new ResultsDatabaseHandler(GameActivity.this);
                                    db.addResult(new Results(username, "Human", "Draw"));
                                    db.close();
                                    ended = true;
                                }
                            }
                        }
                    });
                }
            }
        } else if (gameType.equals("Computer")) {
            changed = false;
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
                                    ResultsDatabaseHandler db = new ResultsDatabaseHandler(GameActivity.this);
                                    db.addResult(new Results(username, "Computer", "Draw"));
                                    db.close();
                                    ended = true;
                                }
                            }

                            if (!ended && changed) {
                                handler.postDelayed(r, 500);
//                                nextMoveForComputer(board, (turn));
//                                turn = 1 - turn;
//                                if (turn == 0)
//                                    currentTurn.setText("Turn: X");
//                                else
//                                    currentTurn.setText("Turn: O");
//                                check(board, currentTurn);
//                                Boolean full = true;
//                                for (int k = 0; k < 3; k++) {
//                                    for (int l = 0; l < 3; l++) {
//                                        if (board.get(k).get(l) == -1) {
//                                            full = false;
//                                            break;
//                                        }
//                                    }
//                                }
//                                if (full) {
//                                    ResultsDatabaseHandler db = new ResultsDatabaseHandler(GameActivity.this);
//                                    db.addResult(new Results(username, "Computer", "Draw"));
//                                    db.close();
//                                    currentTurn.setText("Draw");
//                                    ended = true;
//                                }
                            }

                        }
                    });
                }
            }
        }


    }

    final Runnable r = new Runnable() {
        @Override
        public void run() {
            simulate();
        }
    };

    public void simulate() {
        nextMoveForComputer(board, (turn));
        turn = 1 - turn;
        if (turn == 0)
            currentTurn.setText("Turn: X");
        else
            currentTurn.setText("Turn: O");
        check(board, currentTurn);
    }

    private void nextMoveForComputer(ArrayList<ArrayList<Integer>> board, int turn) {
        Drawable[] symbols = new Drawable[]{cross, zero};
        boolean special = false;
        int[] nextTile = new int[2];
        if ((board.get(1).get(1) == turn && board.get(0).get(0) == (1 - turn) && board.get(2).get(2) == (1 - turn))
                || (board.get(1).get(1) == turn && board.get(0).get(2) == (1 - turn) && board.get(2).get(0) == (1 - turn))) {
            special = true;
        }
        if (board.get(1).get(1) == -1) { // if center is empty Computer fills it
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
        String whoWon = null;
        if (board.get(0).get(0) != -1 &&  board.get(0).get(0) == board.get(0).get(1) && board.get(0).get(1) == board.get(0).get(2)) {
            if (board.get(0).get(0) == 1) {
                text.setText("player O wins!");
                whoWon = "O";
            } else {
                text.setText("player X wins!");
                whoWon = "X";
            }
        } else if (board.get(1).get(0) != -1 &&  board.get(1).get(0) == board.get(1).get(1) && board.get(1).get(1) == board.get(1).get(2)) {
            if (board.get(1).get(0) == 1) {
                text.setText("player O wins!");
                whoWon = "O";
            } else {
                text.setText("player X wins!");
                whoWon = "X";
            }
        } else if (board.get(2).get(0) != -1 &&  board.get(2).get(0) == board.get(2).get(1) && board.get(2).get(1) == board.get(2).get(2)) {
            if (board.get(2).get(0) == 1) {
                text.setText("player O wins!");
                whoWon = "O";
            } else {
                text.setText("player X wins!");
                whoWon = "X";
            }
        } else if (board.get(0).get(0) != -1 &&  board.get(0).get(0) == board.get(1).get(0) && board.get(1).get(0) == board.get(2).get(0)) {
            if (board.get(0).get(0) == 1) {
                text.setText("player O wins!");
                whoWon = "O";
            } else {
                text.setText("player X wins!");
                whoWon = "X";
            }
        } else if (board.get(0).get(1) != -1 &&  board.get(0).get(1) == board.get(1).get(1) && board.get(1).get(1) == board.get(2).get(1)) {
            if (board.get(0).get(1) == 1) {
                text.setText("player O wins!");
                whoWon = "O";
            } else {
                text.setText("player X wins!");
                whoWon = "X";
            }
        } else if (board.get(0).get(2) != -1 &&  board.get(0).get(2) == board.get(1).get(2) && board.get(1).get(2) == board.get(2).get(2)) {
            if (board.get(0).get(2) == 1) {
                text.setText("player O wins!");
                whoWon = "O";
            } else {
                text.setText("player X wins!");
                whoWon = "X";
            }
        } else if (board.get(0).get(0) != -1 &&  board.get(0).get(0) == board.get(1).get(1) && board.get(1).get(1) == board.get(2).get(2)) {
            if (board.get(0).get(0) == 1) {
                text.setText("player O wins!");
                whoWon = "O";
            } else {
                text.setText("player X wins!");
                whoWon = "X";
            }
        } else if (board.get(0).get(2) != -1 &&  board.get(0).get(2) == board.get(1).get(1) && board.get(1).get(1) == board.get(2).get(0)) {
            if (board.get(0).get(2) == 1) {
                text.setText("player O wins!");
                whoWon = "O";
            } else {
                text.setText("player X wins!");
                whoWon = "X";
            }
        } else {
            ended = false;
        }
        if (whoWon != null) {
            ResultsDatabaseHandler db = new ResultsDatabaseHandler(GameActivity.this);
            Results result = new Results();
            String opponentName = null;
            if (gameType.equals("Computer"))
                opponentName = "Computer";
            else
                opponentName = "Human";
            result.set_userName(username);
            result.set_opponentName(opponentName);
            if (whoWon.equals(symbol))
                result.setWinOrLose("Win");
            else
                result.setWinOrLose("Lose");
            db.addResult(result);
            db.close();
        }
    }
}