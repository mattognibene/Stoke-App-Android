package com.stokeapp.stoke.dashboard.weather

import com.stokeapp.stoke.domain.model.WeatherDataModel

sealed class WeatherState {
    data class Success(val data: WeatherDataModel) : WeatherState()
    data class Failure(val error: Throwable) : WeatherState()
}