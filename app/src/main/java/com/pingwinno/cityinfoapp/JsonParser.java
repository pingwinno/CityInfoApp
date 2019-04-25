package com.pingwinno.cityinfoapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pingwinno.cityinfoapp.models.Country;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonParser {
    public static List<Country> parse(String stringData) throws IOException {
        ArrayList<Country> countriesList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, ArrayList<String>> map = mapper.readValue(stringData, Map.class);
        for (Map.Entry country : map.entrySet()) {
            Country countryWithCitiesModel = new Country();
            countryWithCitiesModel.setCountry((String) country.getKey());
            countryWithCitiesModel.setCities((List<String>) country.getValue());
            countriesList.add(countryWithCitiesModel);
        }
        return countriesList;
    }
}
