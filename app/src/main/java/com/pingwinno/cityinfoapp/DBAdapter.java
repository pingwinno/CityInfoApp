package com.pingwinno.cityinfoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.pingwinno.cityinfoapp.models.City;
import com.pingwinno.cityinfoapp.models.Country;

import java.util.ArrayList;
import java.util.List;

public class DBAdapter {
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public DBAdapter(Context context) {
        dbHelper = new DBHelper(context.getApplicationContext());
    }

    public DBAdapter open() {

        database = dbHelper.getWritableDatabase();
        Log.d("OPENEDDB_PATH", database.getPath());
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public class CountryTable {

        public List<Country> getAllCounties() {
            ArrayList<Country> Countrys = new ArrayList<>();
            String orderBy = DBHelper.COLUMN_COUNTRY_NAME + " " + "ASC";
            Cursor cursor = database.query(DBHelper.COUNTRIES_TABLE, new String[]{DBHelper.COLUMN_ID, DBHelper.COLUMN_COUNTRY_NAME},
                    null, null, null, null, orderBy);
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID));
                    String countryName = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_COUNTRY_NAME));
                    Countrys.add(new Country(id, countryName));
                }
                while (cursor.moveToNext());
            }
            cursor.close();
            return Countrys;
        }

        public long getCount() {
            return DatabaseUtils.queryNumEntries(database, DBHelper.COUNTRIES_TABLE);
        }

        public Country getCountry(int id) {
            Country country = null;
            String selection = DBHelper.COLUMN_ID + " = ?";
            Cursor cursor = database.query(DBHelper.COUNTRIES_TABLE, new String[]{DBHelper.COLUMN_ID, DBHelper.COLUMN_COUNTRY_NAME},
                    selection, new String[]{String.valueOf(id)}, null, null, null);
            if (cursor.moveToFirst()) {
                String countryName = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_COUNTRY_NAME));
                country = new Country(id, countryName);
            }
            cursor.close();
            return country;
        }

        public Country getCountry(String countryName) {
            Country country = null;
            String selection = DBHelper.COLUMN_COUNTRY_NAME + " = ?";
            Cursor cursor = database.query(DBHelper.COUNTRIES_TABLE, new String[]{DBHelper.COLUMN_ID, DBHelper.COLUMN_COUNTRY_NAME},
                    selection, new String[]{countryName}, null, null, null);
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID));
                country = new Country(id, countryName);
            }
            cursor.close();
            return country;
        }


        public long insert(Country country) {

            ContentValues cv = new ContentValues();
            cv.put(DBHelper.COLUMN_COUNTRY_NAME, country.getCountryName());

            return database.insert(DBHelper.COUNTRIES_TABLE, null, cv);
        }

        public long delete(long CountryId) {

            String whereClause = DBHelper.COLUMN_ID + " = ?";
            String[] whereArgs = new String[]{String.valueOf(CountryId)};
            return database.delete(DBHelper.COUNTRIES_TABLE, whereClause, whereArgs);
        }

        public long update(Country Country) {

            String whereClause = DBHelper.COLUMN_ID + "=" + Country.getId();
            ContentValues cv = new ContentValues();
            cv.put(DBHelper.COLUMN_COUNTRY_NAME, Country.getCountryName());
            return database.update(DBHelper.COUNTRIES_TABLE, cv, whereClause, null);
        }
    }

    public class CityTable {

        public List<City> getAllCities() {
            ArrayList<City> cities = new ArrayList<>();
            String orderBy = DBHelper.COLUMN_CITY_NAME + " " + "ASC";
            Cursor cursor = database.query(DBHelper.CITIES_TABLE, new String[]{DBHelper.COLUMN_ID, DBHelper.COLUMN_CITY_NAME},
                    null, null, null, null, orderBy);
            if (cursor.moveToFirst()) {
                do {
                    long id = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID));
                    String cityName = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_CITY_NAME));
                    int countryId = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_COUNTRY_ID));
                    cities.add(new City(id, cityName, countryId));
                }
                while (cursor.moveToNext());
            }
            cursor.close();
            return cities;
        }

        public long getCount() {
            return DatabaseUtils.queryNumEntries(database, DBHelper.CITIES_TABLE);
        }

        public City getCity(long id) {
            City City = null;
            String selection = DBHelper.COLUMN_ID + " = ?";
            Cursor cursor = database.query(DBHelper.CITIES_TABLE, new String[]{DBHelper.COLUMN_ID, DBHelper.COLUMN_CITY_NAME},
                    selection, new String[]{String.valueOf(id)}, null, null, null);
            if (cursor.moveToFirst()) {
                String cityName = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_CITY_NAME));
                int countryId = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_COUNTRY_ID));
                City = new City(id, cityName, countryId);
            }
            cursor.close();
            return City;
        }

        public City getCity(String cityName) {
            City City = null;
            String selection = DBHelper.COLUMN_CITY_NAME + " = ?";
            Cursor cursor = database.query(DBHelper.CITIES_TABLE, new String[]{DBHelper.COLUMN_ID, DBHelper.COLUMN_CITY_NAME},
                    selection, new String[]{cityName}, null, null, null);
            if (cursor.moveToFirst()) {
                long id = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID));
                int countryId = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_COUNTRY_ID));
                City = new City(id, cityName, countryId);
            }
            cursor.close();
            return City;
        }

        public City getCity(int countryId) {
            City City = null;
            String selection = DBHelper.COLUMN_COUNTRY_ID + " = ?";
            Cursor cursor = database.query(DBHelper.CITIES_TABLE, new String[]{DBHelper.COLUMN_ID, DBHelper.COLUMN_CITY_NAME},
                    selection, new String[]{String.valueOf(countryId)}, null, null, null);
            if (cursor.moveToFirst()) {
                long id = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID));
                String cityName = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_CITY_NAME));
                City = new City(id, cityName, countryId);
            }
            cursor.close();
            return City;
        }

        public long insert(City City) {

            ContentValues cv = new ContentValues();
            cv.put(DBHelper.COLUMN_CITY_NAME, City.getCityName());
            cv.put(DBHelper.COLUMN_COUNTRY_ID, City.getCountryId());
            return database.insert(DBHelper.CITIES_TABLE, null, cv);
        }

        public long delete(long CityId) {

            String whereClause = DBHelper.COLUMN_ID + " = ?";
            String[] whereArgs = new String[]{String.valueOf(CityId)};
            return database.delete(DBHelper.CITIES_TABLE, whereClause, whereArgs);
        }

        public long update(City City) {

            String whereClause = DBHelper.COLUMN_ID + "=" + City.getId();
            ContentValues cv = new ContentValues();
            cv.put(DBHelper.COLUMN_CITY_NAME, City.getCityName());
            return database.update(DBHelper.CITIES_TABLE, cv, whereClause, null);
        }
    }
}
