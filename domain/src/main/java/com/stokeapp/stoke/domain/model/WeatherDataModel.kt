package com.stokeapp.stoke.domain.model

data class WeatherDataModel(
    val mainDescription: String,
    val tempInKelvin: Float,
    val conditionCode: String,
    val humidityPercentage: Float,
    val windSpeed: Float
)