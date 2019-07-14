package com.stokeapp.stoke.weights

import androidx.lifecycle.ViewModel
import com.stokeapp.stoke.domain.interactor.weights.GetWeights
import com.stokeapp.stoke.domain.interactor.weights.SetWeights
import com.stokeapp.stoke.domain.model.WeightsModel
import com.stokeapp.stoke.util.exhaustive
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class WeightsViewModel @Inject constructor(
    private val getWeightsUseCase: GetWeights,
    private val setWeightsUseCase: SetWeights
) : ViewModel() {

    fun model(): ObservableTransformer<Action, State> {
        return ObservableTransformer { upstream ->
            upstream.flatMap { action ->
                when (action) {
                    is Action.GetWeights -> getWeights()
                    is Action.SetWeights -> setWeights(action)
                }.exhaustive
            }
        }
    }

    private fun getWeights(): Observable<State> {
        return getWeightsUseCase(Unit)
                .map <State> {
                    State.GetWeightsSuccess(weights =
                    WeightsModel(it.surfWeight * 100, it.weatherWeight * 100))
                }
                .onErrorReturn(State::GetWeightsFailure)
                .toObservable()
                .startWith(State.Loading)
    }

    private fun setWeights(action: Action.SetWeights): Observable<State> {
        val params = SetWeights.Params(
                surfWeights = action.surfWeight,
                weatherWeights = action.weatherWeight)
        return setWeightsUseCase.invoke(params)
                .andThen(Observable.just<State>(State.SetWeightsSuccess))
                .onErrorReturn(State::SetWeightsFailure)
    }
}
