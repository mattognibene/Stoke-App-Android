package com.stokeapp.stoke.data.repository

import com.stokeapp.stoke.data.mapper.WeatherModelMapper
import com.stokeapp.stoke.domain.model.WeatherDataModel
import com.stokeapp.stoke.domain.repository.WeatherRepository
import com.stokeapp.stoke.remote.OpenWeatherApi
import io.reactivex.Single
import javax.inject.Inject

class DefaultWeatherRepository @Inject constructor(
    private val api: OpenWeatherApi,
    private val mapper: WeatherModelMapper
) : WeatherRepository {
    override fun getWeatherData(location: String): Single<WeatherDataModel> {
        return api.getWeather(location).map(mapper::map)
    }
}