package com.stokeapp.stoke.domain.repository

import io.reactivex.Single

interface WeatherRepository {
    fun getTemperature(location: String): Single<String>
}