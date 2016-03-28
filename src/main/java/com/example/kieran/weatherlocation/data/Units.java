package com.example.kieran.weatherlocation.data;

import org.json.JSONObject;

/**
 * Created by Kieran on 06/03/2016.
 */
public class Units implements JSONPopulator {
    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void populate(JSONObject data) {
        temperature = data.optString("temperature");
    }
}
