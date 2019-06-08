package com.stokeapp.stoke.domain.model

data class WeatherDataModel(
    val mainDescription: String,
    val tempInKelvin: Float,
    val conditionCode: String
)