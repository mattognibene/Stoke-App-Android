package com.stokeapp.stoke.data.repository

import com.stokeapp.stoke.domain.repository.WeatherRepository
import com.stokeapp.stoke.remote.OpenWeatherApi
import io.reactivex.Single
import javax.inject.Inject

class DefaultWeatherRepository @Inject constructor(
    private val api: OpenWeatherApi
) : WeatherRepository {
    override fun getTemperature(location: String): Single<String> {
        return api.getWeather(location)
                .map { response ->
                    response.main.temp.toString()
                }
    }
}