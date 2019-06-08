package com.stokeapp.stoke.score

object ScoreGenerator {

    var tempInF: Float = 0f
    var conditionCode: String? = null

    fun generateScore(): Float {
        var count = 1f

        if (conditionCode != null) count++

        val sum = generateDescriptionScore() + generateTemperatureScore()
        return sum / count
    }

    fun generateDescriptionScore(): Float {
        val intCode = conditionCode?.toInt() ?: -1
        return when (intCode) {
            800 -> 10f
            in 200..299 -> 0f
            in 300..399 -> 3f // drizzle
            500 -> 2f // light rain
            501 -> 1f // moderate rain
            in 502..599 -> 0f // really bad rain
            in 600..699 -> 0f // snow
            in 700..799 -> 3f // bad atmosphere
            801 -> 8f // light clouds
            802 -> 7f // light medium clouds
            803 -> 6f // medium clouds
            804 -> 5f // overcast
            else -> 0f // todo code is null?
        }
        // TODO what are all possible responses so this else is never hit
    }
    // https://openweathermap.org/weather-conditions

    private fun generateTemperatureScore(): Float {
        return when {
            tempInF < 40f -> 2f
            tempInF < 55 -> 4f
            tempInF < 60 -> 5f
            tempInF < 65 -> 6f
            tempInF < 70 -> 7f
            tempInF < 75f -> 8f
            tempInF < 80f -> 9f
            tempInF < 85f -> 10f
            tempInF < 90f -> 9f
            tempInF < 100f -> 8f
            tempInF < 105f -> 7f
            tempInF < 110f -> 6f
            else -> 4f
        }
    }
}