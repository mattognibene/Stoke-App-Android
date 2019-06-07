package com.stokeapp.stoke.dashboard

sealed class Action {
    data class GetTemperature(val location: String) : Action()
}