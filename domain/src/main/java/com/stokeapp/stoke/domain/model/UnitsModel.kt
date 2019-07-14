package com.stokeapp.stoke.domain.model

data class UnitsModel(
    val temperatureUnits: TemperatureUnits,
    val windUnits: WindUnits
) {
    companion object {
        fun default(): UnitsModel {
            return UnitsModel(TemperatureUnits.FAHRENHEIT, windUnits = WindUnits.METERS_PER_SECOND)
        }
    }
}

enum class TemperatureUnits {
    FAHRENHEIT, KELVIN, CELSIUS
}

enum class WindUnits {
    METERS_PER_SECOND, MILES_PER_HOUR
}