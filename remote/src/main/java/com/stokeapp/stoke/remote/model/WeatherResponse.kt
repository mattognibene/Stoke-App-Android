package com.stokeapp.stoke.remote.model

data class WeatherResponse(
    val coord: Coord,
    val weather: Array<Weather>,
    val base: String,
    val main: Main,
    val visibility: Float,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Int,
    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: String
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

data class Weather(
    val id: String,
    val main: String, // responses like "Clouds", "Rain", "Snow" etc
    val description: String,
    val icon: String
)

data class Wind(
    val speed: Float,
    val deg: Float
)

data class Clouds(
    val all: Float
)

data class Sys(
    val type: Int,
    val id: Int,
    val message: Float,
    val country: String,
    val sunrise: Int,
    val sunset: Int
)