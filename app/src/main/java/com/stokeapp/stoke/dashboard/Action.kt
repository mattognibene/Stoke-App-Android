package com.stokeapp.stoke.dashboard

sealed class Action {
    data class GetCombinedData(val location: Location) : Action()
}