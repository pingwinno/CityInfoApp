package com.pingwinno.cityinfoapp;

import com.pingwinno.cityinfoapp.models.Country;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JsonParserTest {

    @Test
    public void parse() throws IOException {
        List<Country> countries = new ArrayList<>();
        countries.add(new Country("Christmas Island", new ArrayList<String>() {
            {
                add("Flying Fish Cove");
            }
        }));
        countries.add(new Country("Ethiopia", new ArrayList<String>() {
            {
                add("Addis Ababa");
                add("Awasa");
                add("Jijiga");
            }
        }));
        String json = "{\"Christmas Island\":[\"Flying Fish Cove\"],\"Ethiopia\":[\"Addis Ababa\",\"Awasa\",\"Jijiga\"]}";
        assertThat(countries, is(JsonParser.parse(json)));

    }
}