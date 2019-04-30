package com.pingwinno.cityinfoapp.activities.Main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.pingwinno.cityinfoapp.R;
import com.pingwinno.cityinfoapp.activities.CityInfo.CityInfoActivity;
import com.pingwinno.cityinfoapp.activities.Main.adapters.CityAdapter;
import com.pingwinno.cityinfoapp.activities.Main.adapters.CounntryListAdapter;
import com.pingwinno.cityinfoapp.domain.WikiSearch;
import com.pingwinno.cityinfoapp.helpers.CustomTabsSupport;
import com.pingwinno.cityinfoapp.helpers.DataHelper;
import com.pingwinno.cityinfoapp.infrastructure.DBRepository;
import com.pingwinno.cityinfoapp.models.City;
import com.pingwinno.cityinfoapp.models.Country;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        public void onClick(final View view) {
            findViewById(R.id.list).setVisibility(View.GONE);
            findViewById(R.id.progressBar_cyclic).setVisibility(View.VISIBLE);
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            City city = cities.get(position);

            CityInfoRunner request = new CityInfoRunner();
            request.setView(view);
            request.execute(city.getCityName(), countryOfaCity);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
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
                toolbar.setTitle(country.getCountryName());
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

    @Override
    protected void onPause() {
        super.onPause();
        dialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.list).setVisibility(View.VISIBLE);
        findViewById(R.id.progressBar_cyclic).setVisibility(View.GONE);
    }

    private class CityInfoRunner extends AsyncTask<String, Void, String[]> {
        View view;

        public void setView(@NonNull View view) {
            this.view = view;
        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
            String url = null;
            String color = null;
            if (result != null) {
                url = result[0];
                color = result[1];
            }

            if (CustomTabsSupport.isCustomTabsSupported(getApplicationContext())) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setInstantAppsEnabled(true);
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(view.getContext(), Uri.parse(url));
            } else {
                Intent intent = new Intent(MainActivity.this, CityInfoActivity.class);
                intent.putExtra("url", url);

                intent.putExtra("theme-color", color);

                startActivity(intent);
            }
        }

        @Override
        protected String[] doInBackground(String... cityNames) {
            String wikiUrl = "";
            String themeColor = "";
            try {
                wikiUrl = "https://" + WikiSearch.search(cityNames[0], cityNames[1]).getWikipediaUrl();
                //extract "theme-color" value from loaded page
                Document doc = Jsoup.parse(DataHelper.getStringData(wikiUrl));
                for (Element element : doc.getElementsByTag("meta")) {
                    if (element.toString().contains("theme-color")) {
                        String color = element.toString();
                        themeColor = color.substring(color.lastIndexOf("#"), color.lastIndexOf("=") + 9);

                        Log.v("THEMECOLOR", themeColor);
                    }
                }
                Log.v("WIKIURL", wikiUrl);

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (wikiUrl.equals("")) {
                Toast.makeText(MainActivity.this, "Can't found a place", Toast.LENGTH_SHORT).show();
            }
            return new String[]{wikiUrl, themeColor};
        }
    }
}


