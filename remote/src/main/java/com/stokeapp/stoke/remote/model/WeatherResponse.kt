package com.stokeapp.stoke.remote.model

data class WeatherResponse(
    val coord: Coord,
    val main: Main
)

data class Coord(
    val lon: Float,
    val lat: Float
)

data class Main(
    val temp: Float,
    val humidity: Float,
    val pressure: Float,
    val temp_min: Float,
    val temp_max: Float
)