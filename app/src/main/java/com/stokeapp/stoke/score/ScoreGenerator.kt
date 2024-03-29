package com.stokeapp.stoke.score

import com.stokeapp.stoke.domain.model.SurfReportModel
import com.stokeapp.stoke.domain.model.WeatherDataModel
import com.stokeapp.stoke.domain.model.WeightsModel
import com.stokeapp.stoke.util.UnitsConverter
import timber.log.Timber

object ScoreGenerator {

    var weatherData: WeatherDataModel? = null
    var surfReport: SurfReportModel? = null
    var weights: WeightsModel = WeightsModel.default()

    fun generateScore(): Float {
        var weatherScore = 0f
        weatherData?.let { data ->
            weatherScore +=
            generateDescriptionScore(data.conditionCode) * .30f +
            generateTemperatureScore(data.tempInKelvin) * .30f +
            generateHumidityScore(data.humidityPercentage) * .10f +
            generateWindScore(data.windSpeed) * .30f // todo abstract weights
        }

        var surfScore = 0f
        surfReport?.let { report ->
            surfScore += generateMswScore(
                    report.solidRating.toFloat(),
                    report.fadedRating.toFloat()) // TODO min values / adjustments
        }

        Timber.d("Weather Score: $weatherScore")
        Timber.d("Surf Score: $surfScore")
        Timber.d("Weather Weight: ${weights.weatherWeight}")
        Timber.d("Surf Weight: ${weights.surfWeight}")

        return weatherScore * weights.weatherWeight + surfScore * weights.surfWeight
    }

    private fun generateDescriptionScore(conditionCode: String): Float {
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
        val tempInF = UnitsConverter.kelvinToFarenheit(tempInKelvin)
        return when {
            tempInF < 40f -> 2f
            tempInF < 55 -> 4f
            tempInF < 60 -> 5f
            tempInF < 65 -> 6f
            tempInF < 70 -> 7f
            tempInF < 75f -> 8f
            tempInF < 80f -> 9f
            tempInF < 95f -> 10f
            tempInF < 105f -> 9f
            tempInF < 110f -> 8f
            tempInF < 115f -> 7f
            tempInF < 120f -> 6f
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
            else -> 3f
        }
    }

    // Based on beaufort wind scale
    private fun generateWindScore(wind: Float): Float {
        return when {
            wind < 1 -> 9f
            wind < 1.5 -> 10f
            wind < 3.3 -> 9f
            wind < 4 -> 8f
            wind < 5.5 -> 7f
            wind < 8 -> 5f
            wind < 10 -> 4f
            wind < 13 -> 3f
            else -> 0f
        }
    }

    private fun generateMswScore(solidRating: Float, fadedRating: Float): Float {
        return (solidRating + (fadedRating / 2)) + 5.0f
    }
}