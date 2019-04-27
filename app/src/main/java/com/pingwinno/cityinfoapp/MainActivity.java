package com.pingwinno.cityinfoapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.pingwinno.cityinfoapp.models.Country;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Country> countries;


    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();

            Country country = countries.get(position);
            Toast.makeText(MainActivity.this, "You Clicked: " + country.getCountryName(), Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBRepository dbRepository = new DBRepository(this);

        RecyclerView recyclerView = findViewById(R.id.list);
        countries = dbRepository.getCountriesTable().getAllCounties();
        CountryAdapter adapter = new CountryAdapter(this, countries);

        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


}
