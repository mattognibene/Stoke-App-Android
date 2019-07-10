package com.stokeapp.stoke.dashboard

sealed class State {
    data class GetCombinedDataSuccess(val data: CombinedData) : State()
    data class GetCombinedDataFailure(val e: Throwable) : State()
    object Loading : State()
    data class GetLocationSuccess(val locationName: String) : State()
    data class GetLocationFailure(val e: Throwable) : State()
}