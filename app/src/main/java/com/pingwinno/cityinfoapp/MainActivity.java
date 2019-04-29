package com.pingwinno.cityinfoapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.pingwinno.cityinfoapp.models.City;
import com.pingwinno.cityinfoapp.models.Country;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    List<Country> countries;
    List<City> cities;
    RecyclerView recyclerView;
    CityAdapter cityAdapter;
    AlertDialog dialog;
    FloatingActionButton fab;
    DBRepository dbRepository;
    String countryOfaCity;
    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            City city = cities.get(position);
            view.setClickable(false);

            Intent intent = new Intent(MainActivity.this, CityInfoActivity.class);
            try {
                intent.putExtra("url", new ProgressTask().execute(city.getCityName(), countryOfaCity).get());
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.list);
        cities = new ArrayList<>();
        dbRepository = new DBRepository(this);
        countries = dbRepository.getCountriesTable().getAllCounties();
        cityAdapter = new CityAdapter(this, cities);
        recyclerView.setAdapter(cityAdapter);
        cityAdapter.setOnItemClickListener(onItemClickListener);

        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                cityAdapter.notifyDataSetChanged();
            }

        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an country");

        final CounntryListAdapter counntryListAdapter = new CounntryListAdapter(this,
                R.layout.list_item, countries);

        builder.setAdapter(counntryListAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cities.clear();
                final Country country = countries.get(which);
                cities.addAll(dbRepository.getCitiesTable().getCities(country.getId()));
                if (cities.size() < 1) {
                    cities.add(new City() {
                        {
                            setCityName(country.getCountryName());
                        }
                    });
                } else {
                    countryOfaCity = country.getCountryName();
                }
                cityAdapter.notifyDataSetChanged();
            }
        });
        dialog = builder.create();
        dialog.getListView().setFastScrollEnabled(true);
    }

    private class ProgressTask extends AsyncTask<String, Void, String> {
        String wikiUrl;

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }

        @Override
        protected String doInBackground(String... cityNames) {
            {
                try {
                    if (cityNames.length > 1) {
                        wikiUrl = "https://" + WikiSearch.search(cityNames[0], cityNames[1]).getWikipediaUrl();
                    }
                    Log.v("WIKIURL", wikiUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (wikiUrl == null) {
                    Toast.makeText(MainActivity.this, "Can't found a place", Toast.LENGTH_SHORT).show();
                }
            }
            return wikiUrl;
        }
    }
}

