package com.stokeapp.stoke.domain.interactor.weather

import com.stokeapp.stoke.domain.interactor.SingleUseCase
import com.stokeapp.stoke.domain.model.WeatherDataModel
import com.stokeapp.stoke.domain.repository.WeatherRepository
import io.reactivex.Single
import javax.inject.Inject

class GetWeatherData @Inject constructor(
    private val repository: WeatherRepository
) : SingleUseCase<GetWeatherData.Params, WeatherDataModel>() {
    override fun execute(params: Params): Single<WeatherDataModel> {
        return repository.getWeatherData(params.location)
    }

    data class Params(val location: String)
}