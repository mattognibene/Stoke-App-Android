package com.stokeapp.stoke.remote

import com.stokeapp.stoke.remote.model.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {
    @GET("weather/")
    fun getWeather(
        @Query("q") location: String,
        @Query("appid") apiKey: String = "b77cdb8239e5162a9c4a51d2932b3b85" // TODO hide api key
    ): Single<WeatherResponse>
}