package com.pingwinno.cityinfoapp;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class DataHelper {
    public static String getData(String stringUrl) throws IOException {
        URL url = new URL(stringUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String result;
        try (InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream())) {
            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
            result = scanner.hasNext() ? scanner.next() : "";
            inputStream.close();
            scanner.close();
        } finally {
            urlConnection.disconnect();
        }
        return result;
    }
}
