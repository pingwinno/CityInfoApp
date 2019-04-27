package com.pingwinno.cityinfoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    static final String COUNTRIES_TABLE = "countries";
    static final String COLUMN_COUNTRY_NAME = "country";
    static final String CITIES_TABLE = "cities";
    static final String COLUMN_CITY_NAME = "city";
    static final String COLUMN_COUNTRY_ID = "country_id";
    static final String COLUMN_ID = "_id";
    private static final String DATABASE_NAME = "cityinfo.db";
    private static final int SCHEMA = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE countries (" + "_id"
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + "country" + " TEXT);");

        db.execSQL("CREATE TABLE cities (" + "_id"
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + "city" + " TEXT, country_id INTEGER," +
                " CONSTRAINT country_id FOREIGN KEY(_id) REFERENCES countries (_id) " +
                "ON DELETE CASCADE);");
        Log.d("DB_INIT", db.getPath());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE COUNTRIES_TABLE");
        db.execSQL("DROP TABLE CITIES_TABLE");
        onCreate(db);
    }
}
