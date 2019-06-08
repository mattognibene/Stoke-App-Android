package com.stokeapp.stoke.dashboard

import com.stokeapp.stoke.domain.model.WeatherDataModel

sealed class State {
    data class GetWeatherDataSuccess(val data: WeatherDataModel) : State()
    data class GeteatherDataFailure(val e: Throwable) : State()
}