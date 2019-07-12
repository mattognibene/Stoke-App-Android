package com.stokeapp.stoke.weights

import com.stokeapp.stoke.domain.model.WeightsModel

sealed class State {
    data class GetWeightsSuccess(val weights: WeightsModel) : State()
    data class GetWeightsFailure(val error: Throwable) : State()
    object SetWeightsSuccess : State()
    data class SetWeightsFailure(val error: Throwable) : State()
    object Loading : State()
}