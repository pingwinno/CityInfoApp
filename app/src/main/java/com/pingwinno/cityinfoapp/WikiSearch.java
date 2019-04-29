package com.pingwinno.cityinfoapp;

import android.util.Log;

import com.pingwinno.cityinfoapp.models.WikiInfo;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;


public class WikiSearch {
    /*
     Get list of places and search in "summary" field for more precise results
     */
    public static WikiInfo search(String city, String country) throws IOException {
        String placeName = city;
        String queryUrl = String.format("https://secure.geonames.org/wikipediaSearchJSON?q=%s&maxRows=100&username=pingwinno",
                URLEncoder.encode(placeName, "UTF-8"));
        Log.v("REQUEST_URL", queryUrl);
        List<WikiInfo> wikiInfos = JsonParser.parseGeonames(DataHelper.getStringData(queryUrl));
        for (WikiInfo wikiInfo : wikiInfos) {
            Log.v("REQUEST_URL", wikiInfo.getWikipediaUrl());
            if (wikiInfo.getTitle().equals(city)) {
                Log.v("CITY", wikiInfo.getWikipediaUrl());
                if (wikiInfo.getSummary().contains(city) && wikiInfo.getSummary().contains(country)) {
                    Log.v("COUNTRY AND CITY", wikiInfo.getWikipediaUrl());
                    return wikiInfo;
                }
            } else if (wikiInfo.getTitle().contains(city)) {
                Log.v("CITY", wikiInfo.getWikipediaUrl());
                if (wikiInfo.getSummary().contains(city) && wikiInfo.getSummary().contains(country)) {
                    Log.v("COUNTRY AND CITY", wikiInfo.getWikipediaUrl());
                    return wikiInfo;
                }
            }
        }
        //return if matching elements not found
        return search(country);
    }

    /*
       Makes a query to geonames api get first result which contain city/country info
        */
    public static WikiInfo search(String country) throws IOException {
        String queryUrl = String.format("https://secure.geonames.org/wikipediaSearchJSON?q=%s&maxRows=1&username=pingwinno",
                URLEncoder.encode(country, "UTF-8"));
        Log.v("REQUEST_URL", queryUrl);
        WikiInfo result = new WikiInfo();
        for (WikiInfo wikiInfo : JsonParser.parseGeonames(DataHelper.getStringData(queryUrl))) {
            result = wikiInfo;
        }
        return result;
    }
}
