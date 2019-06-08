package com.stokeapp.stoke.dashboard

import androidx.lifecycle.ViewModel
import com.stokeapp.stoke.domain.interactor.weather.GetWeatherData
import com.stokeapp.stoke.util.exhaustive
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getWeatherDataUseCase: GetWeatherData
) : ViewModel() {

    fun model(): ObservableTransformer<Action, State> {
        return ObservableTransformer { upstream ->
            upstream.flatMap { action ->
                when (action) {
                    is Action.GetWeatherData -> getWeatherData(action)
                }.exhaustive
            }
        }
    }

    private fun getWeatherData(action: Action.GetWeatherData): Observable<State> {
        return getWeatherDataUseCase.invoke(GetWeatherData.Params(action.location))
                .map <State>(State::GetWeatherDataSuccess)
                .onErrorReturn(State::GeteatherDataFailure)
                .toObservable()
    }
}