package com.pingwinno.cityinfoapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new ProgressTask().execute();

    }

    class ProgressTask extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... unused) {
            DBRepository dbRepository = new DBRepository(getApplicationContext());

            if ((dbRepository.getCountriesTable().getCount() == 0) &&
                    (dbRepository.getCitiesTable().getCount() == 0)) {
                DBFirstTimeInitializator dbFirstTimeInitializator =
                        new DBFirstTimeInitializator(getApplicationContext());
                dbFirstTimeInitializator.initializeCountries();
                dbFirstTimeInitializator.initializeCities();
            }
            return (null);
        }

        @Override
        protected void onProgressUpdate(Integer... items) {

        }

        @Override
        protected void onPostExecute(Void unused) {
            Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
            SplashScreenActivity.this.startActivity(mainIntent);
            SplashScreenActivity.this.finish();
        }
    }

}
