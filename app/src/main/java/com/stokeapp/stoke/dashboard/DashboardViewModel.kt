package com.stokeapp.stoke.dashboard

import androidx.lifecycle.ViewModel
import com.stokeapp.stoke.dashboard.surf.SurfState
import com.stokeapp.stoke.dashboard.weather.WeatherState
import com.stokeapp.stoke.domain.interactor.surf.GetSurfReport
import com.stokeapp.stoke.domain.interactor.weather.GetWeatherData
import com.stokeapp.stoke.util.exhaustive
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    private val getWeatherDataUseCase: GetWeatherData,
    private val getSurfReportUseCase: GetSurfReport
) : ViewModel() {

    fun model(): ObservableTransformer<Action, State> {
        return ObservableTransformer { upstream ->
            upstream.flatMap { action ->
                when (action) {
                    is Action.GetCombinedData -> getCombinedData(action)
                }.exhaustive
            }
        }
    }

    private fun getWeatherData(openWeatherId: String): Observable<WeatherState> {
        return getWeatherDataUseCase.invoke(GetWeatherData.Params(openWeatherId))
                .map <WeatherState>(WeatherState::Success)
                .onErrorReturn(WeatherState::Failure)
                .toObservable()
    }

    private fun getSurfReport(spotId: String): Observable<SurfState> {
        return getSurfReportUseCase.invoke(GetSurfReport.Params(spotId))
                .map <SurfState>(SurfState::Success)
                .onErrorReturn(SurfState::Failure)
                .toObservable()
    }

    private fun getCombinedData(action: Action.GetCombinedData): Observable<State> {
        return Observable.zip(
                getSurfReport(action.location.mswSpotId),
                getWeatherData(action.location.openWeatherId),
                BiFunction<SurfState, WeatherState, State> { surfState, weatherState ->
                    if (surfState is SurfState.Success && weatherState is WeatherState.Success) {
                        State.GetCombinedDataSuccess(CombinedData(
                                surfData = surfState.report,
                                weatherData = weatherState.data
                        ))
                    } else if (surfState is SurfState.Failure) {
                        State.GetCombinedDataFailure(surfState.error)
                    } else if (weatherState is WeatherState.Failure) {
                        State.GetCombinedDataFailure(weatherState.error)
                    } else {
                        State.GetCombinedDataFailure(Throwable("There was a network error"))
                        // This should never happen
                    }
                })
                .startWith(State.Loading)
    }
}