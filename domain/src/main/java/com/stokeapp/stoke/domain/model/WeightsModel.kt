package com.stokeapp.stoke.domain.model

data class WeightsModel(
    val surfWeight: Float, // in range [0, 1.0f]
    val weatherWeight: Float
) {
    fun sum(): Float {
        return surfWeight + weatherWeight
    }

    companion object {
        fun default(): WeightsModel {
            return WeightsModel(.5f, .5f)
        }
    }
}