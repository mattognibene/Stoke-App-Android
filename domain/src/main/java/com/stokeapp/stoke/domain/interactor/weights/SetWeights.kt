package com.stokeapp.stoke.domain.interactor.weights

import com.stokeapp.stoke.domain.interactor.CompletableUseCase
import com.stokeapp.stoke.domain.model.WeightsModel
import com.stokeapp.stoke.domain.repository.WeightsRepository
import io.reactivex.Completable
import javax.inject.Inject

class SetWeights @Inject constructor(
    private val repository: WeightsRepository
) : CompletableUseCase<SetWeights.Params>() {

    override fun execute(params: Params): Completable {
        val weights = WeightsModel(
                surfWeight = params.surfWeights,
                weatherWeight = params.weatherWeights)
        return repository.setWeights(weights)
    }

    data class Params(val surfWeights: Float, val weatherWeights: Float)
}