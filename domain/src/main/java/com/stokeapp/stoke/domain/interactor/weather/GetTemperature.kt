package com.stokeapp.stoke.domain.interactor.weather

import com.stokeapp.stoke.domain.interactor.SingleUseCase
import com.stokeapp.stoke.domain.repository.WeatherRepository
import io.reactivex.Single
import javax.inject.Inject

class GetTemperature @Inject constructor(
    private val weatherRepository: WeatherRepository
) : SingleUseCase<GetTemperature.Params, String>() {
    override fun execute(params: Params): Single<String> {
        return weatherRepository.getTemperature(params.location)
    }

    data class Params(val location: String)
}