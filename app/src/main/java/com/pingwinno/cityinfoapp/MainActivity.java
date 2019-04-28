package com.pingwinno.cityinfoapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.pingwinno.cityinfoapp.models.City;
import com.pingwinno.cityinfoapp.models.Country;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Country> countries;
    List<City> cities;
    RecyclerView recyclerView;
    CityAdapter cityAdapter;
    AlertDialog dialog;
    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();

            City city = cities.get(position);

            Toast.makeText(MainActivity.this, "You Clicked: " + city.getCityName(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cities = new ArrayList<>();
        final DBRepository dbRepository = new DBRepository(this);

        recyclerView = findViewById(R.id.list);


        countries = dbRepository.getCountriesTable().getAllCounties();

        cityAdapter = new CityAdapter(this, cities);

        recyclerView.setAdapter(cityAdapter);
        cityAdapter.setOnItemClickListener(onItemClickListener);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                cityAdapter.notifyDataSetChanged();
            }

        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an country");

        CounntryListAdapter counntryListAdapter = new CounntryListAdapter(this,
                R.layout.list_item, countries);

        builder.setAdapter(counntryListAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cities.addAll(dbRepository.getCitiesTable().getCities(countries.get(which).getId()));


                cityAdapter.notifyDataSetChanged();

                Toast.makeText(MainActivity.this, "You Clicked: " + countries.get(which).getCountryName()
                        + "   " + cities.size(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog = builder.create();
        dialog.getListView().setFastScrollEnabled(true);

    }


}
