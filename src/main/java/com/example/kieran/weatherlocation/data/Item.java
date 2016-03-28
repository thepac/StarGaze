package com.example.kieran.weatherlocation.data;

import org.json.JSONObject;

/**
 * Created by Kieran on 27/03/2016.
 */
public class Item implements JSONPopulator {
    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void populate(JSONObject data) {
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));


    }
}
