package com.stokeapp.stoke.util

object TemperatureConverter {

    fun kelvinToCelsius(kelvin: Float): Float {
        return kelvin - 273.15f
    }

    fun kelvinToFarenheit(kelvin: Float): Float {
        return celsiusToFarenheit(kelvinToCelsius(kelvin))
    }

    fun celsiusToFarenheit(c: Float): Float {
        return c * (9.0f / 5.0f) + 32
    }
}