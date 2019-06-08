package com.stokeapp.stoke.domain.repository

import com.stokeapp.stoke.domain.model.WeatherDataModel
import io.reactivex.Single

interface WeatherRepository {
    fun getWeatherData(location: String): Single<WeatherDataModel>
}