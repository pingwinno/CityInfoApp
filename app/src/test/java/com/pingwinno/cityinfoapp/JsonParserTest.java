package com.pingwinno.cityinfoapp;

import com.pingwinno.cityinfoapp.helpers.JsonParser;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JsonParserTest {

    @Test
    public void parse() throws IOException {
        Map<String, List<String>> countries = new HashMap<>();
        countries.put("Réunion Island", new ArrayList<String>() {
            {
                add("Flying Fish Cove");
            }
        });
        countries.put("Ethiopia", new ArrayList<String>() {
            {
                add("Addis Ababa");
                add("Awasa");
                add("Jijiga");
            }
        });
        String json = "{\"RÃ©union Island\":[\"Flying Fish Cove\"],\"Ethiopia\":[\"Addis Ababa\",\"Awasa\",\"Jijiga\"]}";
        assertThat(countries, is(JsonParser.parseJsonWithDynamicKeyAndBrokenEncoding(json)));

    }
}