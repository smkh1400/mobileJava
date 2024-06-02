package com.example.xodatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.telephony.IccOpenLogicalChannelResponse;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class ResultsDatabaseHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "resultManager";
    public static final String TABLE_NAME = "results";
    public static final String KEY_ID = "id";
    public static final String KEY_USERNAME = "userName";
    public static final String KEY_OPPONENTNAME = "opponentName";
    public static final String KEY_WINORLOSE = "winOrLose";

    public ResultsDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RESULTS_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_USERNAME +
                " TEXT, " + KEY_OPPONENTNAME + " TEXT, " + KEY_WINORLOSE + " TEXT" + " )";
        db.execSQL(CREATE_RESULTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        String CREATE_RESULTS_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_USERNAME +
                " TEXT, " + KEY_OPPONENTNAME + " TEXT, " + KEY_WINORLOSE + " TEXT" + " )";
        db.execSQL(CREATE_RESULTS_TABLE);
    }

    public void addResult(Results results) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, results.get_userName());
        values.put(KEY_OPPONENTNAME, results.get_opponentName());
        values.put(KEY_WINORLOSE, results.getWinOrLose());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Results getResult(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();

        Results result = new Results();

        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_USERNAME, KEY_OPPONENTNAME, KEY_WINORLOSE},
                KEY_USERNAME + "=?", new String[]{userName}, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            result.set_id(Integer.parseInt(cursor.getString(0)));
            result.set_userName(cursor.getString(1));
            result.set_opponentName(cursor.getString(2));
            result.setWinOrLose(cursor.getString(3));
            db.close();
            cursor.close();
            return result;
        }
        cursor.close();
        db.close();
        return null;
    }

    public ArrayList<Results> showAll(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Results> results = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_USERNAME, KEY_OPPONENTNAME, KEY_WINORLOSE},
                KEY_USERNAME + "=?", new String[]{userName}, null, null, null);
        if (cursor.moveToFirst() && cursor.getCount() > 0) {
            do {
                Results result = new Results(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3));
                results.add(result);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
            return results;
        }

        cursor.close();
        db.close();
        return null;
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
}
