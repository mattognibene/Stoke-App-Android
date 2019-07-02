package com.stokeapp.stoke.dashboard

import com.stokeapp.stoke.domain.model.SurfReportModel
import com.stokeapp.stoke.domain.model.WeatherDataModel

data class CombinedData(
    val surfData: SurfReportModel,
    val weatherData: WeatherDataModel
)