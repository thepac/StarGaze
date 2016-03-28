package com.example.kieran.weatherlocation.service;

import com.example.kieran.weatherlocation.data.Channel;

/**
 * Created by Kieran on 06/03/2016.
 */
public interface WeatherServiceCallBack {
    void ServiceSuccess(Channel channel);
    void ServiceFailure(Exception exception);

}
