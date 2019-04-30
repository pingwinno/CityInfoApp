package com.pingwinno.cityinfoapp.activities.SplashScreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.pingwinno.cityinfoapp.R;
import com.pingwinno.cityinfoapp.activities.Main.MainActivity;
import com.pingwinno.cityinfoapp.infrastructure.DBFirstTimeInitializator;
import com.pingwinno.cityinfoapp.infrastructure.DBRepository;

import pl.droidsonroids.gif.GifImageView;

import static android.content.Intent.FLAG_ACTIVITY_NO_HISTORY;

public class SplashScreenActivity extends AppCompatActivity {
    SharedPreferences appPref;
    Intent mainIntent;
    private boolean isNetworkAvailable = true;

    //use for color offset
    private static int darkenColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f;
        return Color.HSVToColor(hsv);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!isNetworkAvailable) {
            System.exit(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        final TextView messageTextView = findViewById(R.id.message_view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(darkenColor(
                    getResources().getColor(R.color.splashScreen)));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.splashScreen));
        }
        GifImageView gifImageView = findViewById(R.id.gif_view);
        new Intent(getIntent()).setFlags(FLAG_ACTIVITY_NO_HISTORY);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        appPref = getSharedPreferences("DBState", Context.MODE_PRIVATE);
        mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
        if (activeNetworkInfo == null) {
            messageTextView.setText(R.string.NoConnectionFirst);
            Toast.makeText(this, "Network is disabled. Please enable it and restart app", Toast.LENGTH_LONG).show();
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
            if (appPref.getBoolean("isDbInitialized", false)) {
                SplashScreenActivity.this.startActivity(mainIntent);
                SplashScreenActivity.this.finish();
            } else {
                new DataInitialization().execute();
            }
        }
    }

    private class DataInitialization extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... unused) {
            Log.v("DB_INIT", "Start...");
            DBRepository dbRepository = new DBRepository(getApplicationContext());
            dbRepository.getCitiesTable().clearTable();
            dbRepository.getCountriesTable().clearTable();
            DBFirstTimeInitializator dbFirstTimeInitializator =
                    new DBFirstTimeInitializator(getApplicationContext());
            dbFirstTimeInitializator.initializeCountries();
            dbFirstTimeInitializator.initializeCities();

            dbRepository.close();
            SharedPreferences.Editor editor = appPref.edit();
            editor.putBoolean("isDbInitialized", true);
            editor.apply();
            editor.commit();
            return (null);
        }

        @Override
        protected void onProgressUpdate(Integer... items) {

        }

        @Override
        protected void onPostExecute(Void unused) {
            SplashScreenActivity.this.startActivity(mainIntent);
            SplashScreenActivity.this.finish();
        }
    }
}
