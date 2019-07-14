package com.stokeapp.stoke.weights

sealed class Action {
    object GetWeights : Action()
    data class SetWeights(val surfWeight: Float, val weatherWeight: Float) : Action()
}