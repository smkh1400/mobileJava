package com.example.xodatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class GameStateDatabaseHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "gameStateManager";
    public static final String TABLE_NAME = "gameStates";
    public static final String KEY_ID = "id";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_GAMETYPE = "gameType";
    public static final String KEY_SYMBOL = "symbol";
    public static final String KEY_BOARD = "board";

    public static String boardToString(ArrayList<ArrayList<Integer>> board) {

        // -1 to 0, 0 to 1 and 1 to 2

        String result = "";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result += board.get(i).get(j) + 1;
            }
        }
        return result;
    }

    public static ArrayList<ArrayList<Integer>> stringToBoard(String string) {
        // 0 to -1, 1 to 0 and 2 to 1

        ArrayList<ArrayList<Integer>> board = new ArrayList<>();
        int index = 0;
        for (int i = 0; i < 3; i++) {
            ArrayList<Integer> boardRow = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                boardRow.add(Integer.parseInt(string.substring(index, index + 1)) -1);
                index += 1;
            }
            board.add(boardRow);
        }

        return board;
    }

    public GameStateDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GAMESTATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_USERNAME + " TEXT, " + KEY_GAMETYPE + " TEXT, " +
                KEY_SYMBOL + " TEXT, " + KEY_BOARD + " TEXT" + " )";
        db.execSQL(CREATE_GAMESTATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addGameState(GameStates gameState) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, gameState.get_username());
        values.put(KEY_GAMETYPE, gameState.get_gameType());
        values.put(KEY_SYMBOL, gameState.get_symbol());
        values.put(KEY_BOARD, boardToString(gameState.get_board()));

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public GameStates getGameState(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        GameStates gameState = new GameStates();

        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_USERNAME, KEY_GAMETYPE, KEY_SYMBOL, KEY_BOARD},
                KEY_USERNAME + "=?", new String[]{username}, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            gameState.set_id(Integer.parseInt(cursor.getString(0)));
            gameState.set_username(cursor.getString(1));
            gameState.set_gameType(cursor.getString(2));
            gameState.set_symbol(cursor.getString(3));
            gameState.set_board(stringToBoard(cursor.getString(4)));
            db.close();
            cursor.close();
            return gameState;
        }
        cursor.close();
        db.close();
        return null;
    }

    public void updateGameState(GameStates gameState) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        int result;

        values.put(KEY_USERNAME, gameState.get_username());
        values.put(KEY_GAMETYPE, gameState.get_gameType());
        values.put(KEY_SYMBOL, gameState.get_symbol());
        values.put(KEY_BOARD, GameStateDatabaseHandler.boardToString(gameState.get_board()));

        Log.w("getBoard", boardToString(gameState.get_board()));

        result = db.update(TABLE_NAME, values, KEY_ID + "=?", new String[]{String.valueOf(gameState.get_id())});
        Log.w("result", String.valueOf(result));
        db.close();

        GameStates deb = this.getGameState(gameState._username);
        Log.w("what", boardToString(deb.get_board()));

    }

    public int getNumberOfGameStates(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_USERNAME, KEY_GAMETYPE, KEY_SYMBOL, KEY_BOARD},
                KEY_USERNAME + "=?", new String[]{username}, null, null, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;

    }

    public void deleteGameState(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_USERNAME + "=?", new String[]{username});
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
}
