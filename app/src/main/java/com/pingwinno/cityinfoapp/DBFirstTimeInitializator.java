package com.pingwinno.cityinfoapp;

import android.content.Context;
import android.util.Log;

import com.pingwinno.cityinfoapp.models.City;
import com.pingwinno.cityinfoapp.models.Country;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DBFirstTimeInitializator {
    private Context context;
    private Map<String, List<String>> countriesWithCities;

    public DBFirstTimeInitializator(Context context) {
        this.context = context;
        try {
            countriesWithCities = JsonParser.parse(DataHelper.getData(
                    "https://raw.githubusercontent.com/David-Haim/CountriesToCitiesJSON/master/countriesToCities.json"));
        } catch (IOException e) {
            Log.e("GET_DATA", "Can't get data. Exception: " + e.toString());
        }
    }

    public void initializeCountries() {
        Log.d("DB_INIT", "Start");

        DBRepository dbRepository = new DBRepository(context);
        for (Map.Entry<String, List<String>> entry : countriesWithCities.entrySet()) {
            Country country = new Country();
            country.setCountryName(entry.getKey());
            dbRepository.getCountriesTable().insert(country);

        }
        dbRepository.close();
    }

    public void initializeCities() {
        Log.d("DB_INIT", "Start");

        DBRepository dbRepository = new DBRepository(context);

        for (Map.Entry<String, List<String>> entry : countriesWithCities.entrySet()) {

            int countryId = dbRepository.getCountriesTable().getCountry(entry.getKey()).getId();
            for (String cityString : entry.getValue()) {
                City city = new City();
                city.setCityName(cityString);
                city.setCountryId(countryId);
                dbRepository.getCitiesTable().insert(city);
            }
        }
        dbRepository.close();
    }
}
