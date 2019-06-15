package com.stokeapp.stoke.dashboard

sealed class Action {
    data class GetWeatherData(val location: String) : Action()
    data class GetSurfReport(val spotId: String) : Action()
}