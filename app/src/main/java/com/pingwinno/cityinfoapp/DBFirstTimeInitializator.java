package com.pingwinno.cityinfoapp;

import android.content.Context;
import android.util.Log;

import com.pingwinno.cityinfoapp.models.City;
import com.pingwinno.cityinfoapp.models.Country;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBFirstTimeInitializator {
    public static void initialize(Context context) {
        Map<String, List<String>> countriesWithCities = new HashMap<>();
        Log.d("DB_INIT", "Start");
        try {
            countriesWithCities = JsonParser.parse(DataHelper.getData(
                    "https://raw.githubusercontent.com/David-Haim/CountriesToCitiesJSON/master/countriesToCities.json"));
        } catch (IOException e) {
            Log.e("GET_DATA", "Can't get data. Exception: " + e.toString());
        }
        DBAdapter dbAdapter = new DBAdapter(context);
        dbAdapter.open();
        for (Map.Entry<String, List<String>> entry : countriesWithCities.entrySet()) {
            DBAdapter.CountryTable countryTable = dbAdapter.new CountryTable();
            Country country = new Country();
            country.setCountryName(entry.getKey());
            int countryId = (int) countryTable.insert(country);
            for (String cityString : entry.getValue()) {
                DBAdapter.CityTable cityTable = dbAdapter.new CityTable();
                City city = new City();
                city.setCityName(cityString);
                city.setCountryId(countryId);
                cityTable.insert(city);
            }
        }
        dbAdapter.close();
    }
}
