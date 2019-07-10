package com.stokeapp.stoke.location

sealed class State {
    object WriteLocationSuccess : State()
    data class WriteLocationFailure(val e: Throwable) : State()
}