package com.pingwinno.cityinfoapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import pl.droidsonroids.gif.GifImageView;

public class SplashScreenActivity extends AppCompatActivity {
    private boolean isNetworkAvailable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        GifImageView gifImageView = findViewById(R.id.gif_view);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            Toast.makeText(this, "Network is disabled. Please enable it and restart app", Toast.LENGTH_LONG).show();
            gifImageView.setImageResource(R.drawable.car_no_connection);
            isNetworkAvailable = false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SplashScreenActivity.this,
                            "But you can keep looking at a cat.\n " +
                                    "It cute. Isn't?",
                            Toast.LENGTH_LONG).show();
                }
            }, 10 * 1000);
        } else {
            DBRepository dbRepository = new DBRepository(getApplicationContext());
            if ((dbRepository.getCountriesTable().getCount() != 0) &&
                    (dbRepository.getCitiesTable().getCount() != 0)) {
                Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                SplashScreenActivity.this.startActivity(mainIntent);
                dbRepository.close();
                SplashScreenActivity.this.finish();
            } else {
                new ProgressTask().execute();
            }

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!isNetworkAvailable) {
            System.exit(0);
        }
    }

    private class ProgressTask extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... unused) {
            {
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
