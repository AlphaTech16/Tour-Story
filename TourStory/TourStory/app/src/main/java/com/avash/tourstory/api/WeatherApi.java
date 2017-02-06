package com.avash.tourstory.api;

import com.avash.tourstory.model.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface WeatherApi {
    @GET()
    Call<Weather>getWeatherData(@Url String url);

}
