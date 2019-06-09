package com.stokeapp.stoke.score

import com.stokeapp.stoke.domain.model.WeatherDataModel
import com.stokeapp.stoke.util.TemperatureConverter

object ScoreGenerator {

    var weatherData: WeatherDataModel? = null

    fun generateScore(): Float {
        weatherData?.let { data ->
            val sum = generateDescriptionScore(data.conditionCode) +
                    generateTemperatureScore(data.tempInKelvin) +
                    generateHumidityScore(data.humidityPercentage) // TODO these should be weighted
            return sum / 3
        }
        return -1f
    }

    fun generateDescriptionScore(conditionCode: String): Float {
        val intCode = conditionCode.toInt()
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

    private fun generateTemperatureScore(tempInKelvin: Float): Float {
        val tempInF = TemperatureConverter.kelvinToFarenheit(tempInKelvin)
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

    private fun generateHumidityScore(humidity: Float): Float {
        return when {
            humidity < 25 -> 4f
            humidity < 30 -> 6f
            humidity < 40 -> 9f
            humidity < 55f -> 10f
            humidity < 70 -> 6f
            else -> 4f
        }
    }
}