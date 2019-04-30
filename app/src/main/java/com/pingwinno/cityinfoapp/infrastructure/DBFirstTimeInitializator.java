package com.pingwinno.cityinfoapp.infrastructure;

import android.content.Context;
import android.util.Log;

import com.pingwinno.cityinfoapp.helpers.DataHelper;
import com.pingwinno.cityinfoapp.helpers.JsonParser;
import com.pingwinno.cityinfoapp.models.City;
import com.pingwinno.cityinfoapp.models.Country;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DBFirstTimeInitializator {
    private Context context;
    private Map<String, List<String>> countriesWithCities;

    public DBFirstTimeInitializator(Context context) {
        this.context = context;
        try {
            countriesWithCities = JsonParser.parseJsonWithDynamicKeyAndBrokenEncoding(DataHelper.getStringData(
                    "https://raw.githubusercontent.com/David-Haim/CountriesToCitiesJSON/master/countriesToCities.json"));
        } catch (IOException e) {
            Log.e("GET_DATA", "Can't get data. Exception: " + e.toString());
        }
    }

    public void initializeCountries() {
        Log.d("DB_INIT", "Start");

        DBRepository dbRepository = new DBRepository(context);
        List<Country> countries = new ArrayList<>();
        for (String countryString : countriesWithCities.keySet()) {
            if (!countryString.trim().equals("")) {
                Country country = new Country();
                country.setCountryName(countryString);
                countries.add(country);
            }
        }
        dbRepository.getCountriesTable().insert(countries);
        dbRepository.close();
    }

    public void initializeCities() {
        Log.d("DB_INIT", "Start");

        DBRepository dbRepository = new DBRepository(context);
        List<City> cities = new ArrayList<>();
        for (String countryString : countriesWithCities.keySet()) {
            if (!countryString.trim().equals("")) {
                int countryId = dbRepository.getCountriesTable().getCountry(countryString).getId();
                for (String cityString : countriesWithCities.get(countryString)) {
                    if (!cityString.trim().equals("")) {
                        City city = new City();
                        city.setCityName(cityString);
                        city.setCountryId(countryId);
                        cities.add(city);
                    }
                }
            }
        }
        dbRepository.getCitiesTable().insert(cities);
        dbRepository.close();
    }
}
