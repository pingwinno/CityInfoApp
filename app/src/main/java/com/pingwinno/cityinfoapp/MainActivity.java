package com.pingwinno.cityinfoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AutoCompleteTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autocomplete);
        DBAdapter dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        DBAdapter.CountryTable countryTable = dbAdapter.new CountryTable();


    }
}
