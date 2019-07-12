package com.stokeapp.stoke.domain.interactor.weights

import com.stokeapp.stoke.domain.interactor.SingleUseCase
import com.stokeapp.stoke.domain.model.WeightsModel
import com.stokeapp.stoke.domain.repository.WeightsRepository
import io.reactivex.Single
import javax.inject.Inject

class GetWeights @Inject constructor(
    private val repository: WeightsRepository
) : SingleUseCase<Unit, WeightsModel>() {
    override fun execute(params: Unit): Single<WeightsModel> {
        return repository.getWeights()
                .onErrorReturn {
                    WeightsModel.equalSplit()
                }
    }
}