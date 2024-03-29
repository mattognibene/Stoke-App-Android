package com.stokeapp.stoke.dashboard

import com.stokeapp.stoke.domain.model.UnitsModel
import com.stokeapp.stoke.domain.model.WeightsModel

sealed class State {
    data class GetCombinedDataSuccess(val data: CombinedData) : State()
    data class GetCombinedDataFailure(val e: Throwable) : State()
    object Loading : State()
    data class GetLocationSuccess(val locationName: String) : State()
    data class GetLocationFailure(val e: Throwable) : State()
    data class GetWeightsSuccess(val weights: WeightsModel) : State()
    data class GetWeightsFailure(val e: Throwable) : State()
    data class GetUnitsSuccess(val units: UnitsModel) : State()
    data class GetUnitsFailure(val e: Throwable) : State()
}