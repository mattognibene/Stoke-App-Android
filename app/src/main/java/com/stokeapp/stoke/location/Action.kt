package com.stokeapp.stoke.location

sealed class Action {
    data class WriteLocation(val locationName: String) : Action()
}