package com.stokeapp.stoke.data.mapper

import com.stokeapp.stoke.domain.common.Mapper
import com.stokeapp.stoke.domain.model.WeatherDataModel
import com.stokeapp.stoke.remote.model.WeatherResponse
import javax.inject.Inject

class WeatherModelMapper @Inject constructor() : Mapper<WeatherResponse, WeatherDataModel> {
    override fun map(t: WeatherResponse): WeatherDataModel {
        return WeatherDataModel(
                mainDescription = t.weather[0].main,
                tempInKelvin = t.main.temp
        )
    }
}