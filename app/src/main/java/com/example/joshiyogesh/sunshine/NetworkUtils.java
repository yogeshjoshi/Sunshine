package com.example.joshiyogesh.sunshine;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Joshi Yogesh on 01/01/2017.
 */

public class NetworkUtils {

    /*Here would be orignial URL of API .. */
    private static final String DYNAMIC_WEATHER_URL =
            "https://andfun-weather.udacity.com/weather";
    private static final String STATIC_WEATHER_URL =
            "https://andfun-weather.udacity.com/staticweather";
    private static final String FORECAST_BASED_URL =
            STATIC_WEATHER_URL;

//    the format we want from our api to return is json

    private static final String format = "json";

//    the number of days we would like to know our user is 14 days that we can increase and decrease ..

    private static final int numDays = 14;
    /*the units we want our API to return ...*/

    private static final String units = "metric";
    /*here we will include sorting of data ... so that we can receive data according to users request */

    final static String QUERY_PARAM = "q";
    final static String LAT_PARAM = "lat";
    final static String LON_PARAM = "lon";
    final static String FORMAT_PARAM = "mode";
    final static String UNITS_PARAM = "units";
    final static String DAYS_PARAM = "cnt";
    /*method which query to build URL which is required  to fetch the data */

    protected static URL buildUrl(String locationQuery){
        Uri builtUri = Uri.parse(FORECAST_BASED_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM,locationQuery)
                .appendQueryParameter(FORMAT_PARAM,format)
                .appendQueryParameter(UNITS_PARAM,units)
                .appendQueryParameter(DAYS_PARAM,Integer.toString(numDays))
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        }catch (MalformedURLException mfe){
            mfe.printStackTrace();
        }
        return url;
    }

    protected static String getResponseFromHttpUrl (URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        }finally {
            urlConnection.disconnect();
        }
    }

}
