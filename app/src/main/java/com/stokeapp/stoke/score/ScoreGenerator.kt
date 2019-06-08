package com.stokeapp.stoke.score

object ScoreGenerator {

    var tempInF: Float = 0f

    fun generateScore(): Float {
        return generateTemperatureScore()
    }

    private fun generateTemperatureScore(): Float {
        return if (tempInF < 40f) {
            2f
        } else if (tempInF < 55) {
            4f
        } else if (tempInF < 60) {
            5f
        } else if (tempInF < 65) {
            6f
        } else if (tempInF < 70) {
            7f
        } else if (tempInF < 75f) {
            8f
        } else if (tempInF < 80f) {
            9f
        } else if (tempInF < 85f) {
            10f
        } else if (tempInF < 90f) {
            9f
        } else if (tempInF < 100f) {
            8f
        } else if (tempInF < 105f) {
            7f
        } else if (tempInF < 110f) {
            6f
        } else {
            4f
        }
    }
}