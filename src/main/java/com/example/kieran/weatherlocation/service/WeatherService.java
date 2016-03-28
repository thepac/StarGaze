package com.example.kieran.weatherlocation.service;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.kieran.weatherlocation.data.Channel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Kieran on 06/03/2016.
 */
public class WeatherService {
    private WeatherServiceCallBack callBack;
    private String location;
    private Exception error;

    public WeatherService(WeatherServiceCallBack callBack) {
        this.callBack = callBack;
    }

    public String getLocation() {
        return location;
    }

    public void refreshWeather(final String l){
        this.location = l;

        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {

                String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\")and u='c'", strings[0]);
                String endpoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));
            try {
                URL url = new URL(endpoint);

                URLConnection connection = url.openConnection();

                InputStream inputStream = connection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) !=null){
                    result.append(line);
                }
                return result.toString();

            }  catch (IOException e) {
                error = e;
            }
                return null;

            }

            @Override
            protected void onPostExecute(String s) {

                if (s == null && error != null){
                    callBack.ServiceFailure(error);
                    return;
                }
                try {
                    JSONObject data = new JSONObject(s);
                    JSONObject queryResults = data.optJSONObject("query");

                    int count = queryResults.optInt("count");
                    if (count == 0){
                        callBack.ServiceFailure(new LocationWeatherException("No weather info found for" +location) );
                        return;

                    }

                    Channel channel = new Channel();
                    channel.populate(queryResults.optJSONObject("results").optJSONObject("channel"));

                    callBack.ServiceSuccess(channel);

                } catch (JSONException e) {
                    callBack.ServiceFailure(e);
                }
            }
        }.execute(location);

    }
    public class LocationWeatherException extends Exception{
        public LocationWeatherException(String detailMessage) {
            super(detailMessage);
        }
    }
}
