package com.example.xodatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Arrays;

public class UsersDatabaseHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "userManager";
    public static final String TABLE_NAME = "users";
    public static final String KEY_ID = "id";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_NICKNAME = "nickname";

    public UsersDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_USERNAME +
                " TEXT, " + KEY_PASSWORD + " TEXT, " + KEY_NICKNAME + " TEXT" + " )";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_USERNAME +
                " TEXT, " + KEY_PASSWORD + " TEXT, " + KEY_NICKNAME + " TEXT" + " )";
        db.execSQL(CREATE_USERS_TABLE);
    }

    public void addUser(Users users) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, users.get_username());
        values.put(KEY_PASSWORD, users.get_password());
        values.put(KEY_NICKNAME, users.get_nickname());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Users getUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Users user = new Users();

        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_USERNAME, KEY_PASSWORD, KEY_NICKNAME},
                KEY_USERNAME + "=?" + " AND " + KEY_PASSWORD + "=?", new String[]{username, password}, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            user.set_id(Integer.parseInt(cursor.getString(0)));
            user.set_username(cursor.getString(1));
            user.set_password(cursor.getString(2));
            user.set_nickname(cursor.getString(3));
            return user;
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
