package com.stokeapp.stoke.units

import androidx.lifecycle.ViewModel
import com.stokeapp.stoke.domain.interactor.units.GetUnits
import com.stokeapp.stoke.domain.interactor.units.SetUnits
import com.stokeapp.stoke.domain.model.TemperatureUnits
import com.stokeapp.stoke.domain.model.UnitsModel
import com.stokeapp.stoke.domain.model.WindUnits
import com.stokeapp.stoke.util.exhaustive
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import java.security.InvalidParameterException
import javax.inject.Inject

class UnitsViewModel @Inject constructor(
    private val getUnitsUseCase: GetUnits,
    private val setUnitsUseCase: SetUnits
) : ViewModel() {

    fun model(): ObservableTransformer<Action, State> {
        return ObservableTransformer { upstream ->
            upstream.flatMap { action ->
                when (action) {
                    is Action.GetUnits -> getUnits()
                    is Action.SetUnits -> setUnits(action)
                }.exhaustive
            }
        }
    }

    private fun getUnits(): Observable<State> {
        return getUnitsUseCase(Unit)
                .map<State> {
                    State.GetUnitsSuccess(
                            mapWindToStringArray(it.windUnits),
                            mapTemperatureToStringArray(it.temperatureUnits)
                    )
                }
                .onErrorReturn(State::GetUnitsFailure)
                .toObservable()
                .startWith(State.Loading)
    }

    private fun setUnits(action: Action.SetUnits): Observable<State> {
        val params = SetUnits.Params(mapToUnitsModel(action))
        return setUnitsUseCase.invoke(params)
                .andThen(Observable.just<State>(State.SetUnitsSuccess))
                .onErrorReturn(State::SetUnitsFailure)
    }

    private fun mapTemperatureToStringArray(temperatureUnits: TemperatureUnits): String {
        return when (temperatureUnits) {
            TemperatureUnits.FAHRENHEIT -> "Fahrenheit"
            TemperatureUnits.KELVIN -> "Kelvin"
            TemperatureUnits.CELSIUS -> "Celsius"
        }.exhaustive
    }

    private fun mapWindToStringArray(windUnits: WindUnits): String {
        return when (windUnits) {
            WindUnits.METERS_PER_SECOND -> "m/s"
            WindUnits.MILES_PER_HOUR -> "mph"
        }.exhaustive
    }

    private fun mapToUnitsModel(action: Action.SetUnits): UnitsModel {
        val tempEnum = when (action.temperatureName) {
            "Kelvin" -> TemperatureUnits.KELVIN
            "Celsius" -> TemperatureUnits.CELSIUS
            "Fahrenheit" -> TemperatureUnits.FAHRENHEIT
            else -> throw InvalidParameterException("Error: temperature unit not supported")
            // TODO probably not throw an exception
        }.exhaustive

        val windEnum = when (action.windName) {
            "m/s" -> WindUnits.METERS_PER_SECOND
            "mph" -> WindUnits.MILES_PER_HOUR
            else -> throw InvalidParameterException("Error: wind unit not supported")
            // TODO probably not throw an exception
        }.exhaustive
        return UnitsModel(
                temperatureUnits = tempEnum,
                windUnits = windEnum
        )
    }
}
