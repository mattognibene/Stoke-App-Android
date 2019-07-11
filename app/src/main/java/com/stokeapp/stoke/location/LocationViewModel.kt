package com.stokeapp.stoke.location

import androidx.lifecycle.ViewModel
import com.stokeapp.stoke.domain.interactor.location.WriteLocation
import com.stokeapp.stoke.util.exhaustive
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class LocationViewModel @Inject constructor(
    private val writeLocationUseCase: WriteLocation
) : ViewModel() {

    fun model(): ObservableTransformer<Action, State> {
        return ObservableTransformer { upstream ->
            upstream.flatMap { action ->
                when (action) {
                    is Action.WriteLocation -> writeLocation(action)
                }.exhaustive
            }
        }
    }

    private fun writeLocation(action: Action.WriteLocation): Observable<State> {
        val params = WriteLocation.Params(locationName = action.locationName)
        return writeLocationUseCase.invoke(params)
                .andThen(Observable.just<State>(State.WriteLocationSuccess))
                .onErrorReturn(State::WriteLocationFailure)
    }
}