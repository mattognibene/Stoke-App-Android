package com.stokeapp.stoke.dashboard

import androidx.lifecycle.ViewModel
import com.stokeapp.stoke.domain.interactor.weather.GetTemperature
import com.stokeapp.stoke.util.exhaustive
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getTemperatureUseCase: GetTemperature
) : ViewModel() {

    fun model(): ObservableTransformer<Action, State> {
        return ObservableTransformer { upstream ->
            upstream.flatMap { action ->
                when (action) {
                    is Action.GetTemperature -> getTemperature(action)
                }.exhaustive
            }
        }
    }

    private fun getTemperature(action: Action.GetTemperature): Observable<State> {
        return getTemperatureUseCase.invoke(GetTemperature.Params(action.location))
                .map <State> { State.GetTemperatureSuccess(it) }
                .onErrorReturn(State::GetTemeperatureFailure)
                .toObservable()
    }
}