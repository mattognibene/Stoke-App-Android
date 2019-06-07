package com.stokeapp.stoke.dashboard

sealed class State {
    data class GetTemperatureSuccess(val celsius: String) : State()
    data class GetTemeperatureFailure(val e: Throwable) : State()
}