package com.stokeapp.stoke.units

sealed class State {
    data class GetUnitsSuccess(val windName: String, val temperatureName: String) : State()
    data class GetUnitsFailure(val error: Throwable) : State()
    object SetUnitsSuccess : State()
    data class SetUnitsFailure(val error: Throwable) : State()
    object Loading : State()
}