package com.pingwinno.cityinfoapp;

import android.util.Log;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pingwinno.cityinfoapp.models.WikiInfo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonParser {
    /*Use for https://raw.githubusercontent.com/David-Haim/CountriesToCitiesJSON/master/countriesToCities.json
    which encoded in Windows-1252
     */
    public static Map<String, List<String>> parseJsonWithDynamicKeyAndBrokenEncoding(String stringData) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new String(stringData.getBytes("Windows-1252"), "UTF-8"), Map.class);
    }

    public static List<WikiInfo> parseGeonames(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.createObjectNode();
        JsonFactory factory = mapper.getFactory();
        com.fasterxml.jackson.core.JsonParser parser = factory.createParser(jsonString);
        JsonNode geoNamesArray = mapper.readTree(parser);
        Log.v("JSONNODE_CONTENT", "Content; " + geoNamesArray.get("geonames").toString());
        List<WikiInfo> wikiInfo = new ObjectMapper().readValue(geoNamesArray.get("geonames").toString(), new TypeReference<List<WikiInfo>>() {
        });
        return wikiInfo;

    }
}
