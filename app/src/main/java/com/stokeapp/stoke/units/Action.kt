package com.stokeapp.stoke.units

sealed class Action {
    object GetUnits : Action()
    data class SetUnits(val temperatureName: String, val windName: String) : Action()
}