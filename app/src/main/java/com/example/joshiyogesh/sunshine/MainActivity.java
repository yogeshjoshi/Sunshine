package com.example.joshiyogesh.sunshine;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ProgressBar mLoadingIndicator;
    TextView mWeatherTextView,mErrorMessageDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWeatherTextView = (TextView)findViewById(R.id.tv_weather_data);
        mErrorMessageDisplay = (TextView)findViewById(R.id.tv_error_message);
        mLoadingIndicator = (ProgressBar)findViewById(R.id.progressBar);
        loadWeatherData();

    }

    private void loadWeatherData() {
            showWeatherDataView();

            new FetchWeatherTask().execute();
    }

    /**
     * This method will make the View for the weather data visible and
     * hide the error message.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showWeatherDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        mWeatherTextView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the weather
     * View.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mWeatherTextView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }
    public class FetchWeatherTask extends AsyncTask<String , Void,String[]>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... params) {

            /* If there's no zip code, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }

            String location = params[0];
            URL weatherRequestUrl = NetworkUtils.buildUrl(location);
            try {
                String jsonWeatherResponce= NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return new String[0];
        }

        @Override
        protected void onPostExecute(String[] weatherData ){
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if(weatherData!=null){
                for (String weatherString : weatherData) {
                    mWeatherTextView.append((weatherString) + "\n\n\n");
                }
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.forecast,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            mWeatherTextView.setText("");
            loadWeatherData();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

}
