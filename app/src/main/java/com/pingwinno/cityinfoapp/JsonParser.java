package com.pingwinno.cityinfoapp;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonParser {
    public static Map<String, List<String>> parse(String stringData) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new String(stringData.getBytes("Windows-1252"), "UTF-8"), Map.class);
    }
}
