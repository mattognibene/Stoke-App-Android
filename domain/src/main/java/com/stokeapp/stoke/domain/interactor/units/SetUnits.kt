package com.stokeapp.stoke.domain.interactor.units

import com.stokeapp.stoke.domain.interactor.CompletableUseCase
import com.stokeapp.stoke.domain.model.UnitsModel
import com.stokeapp.stoke.domain.repository.UnitsRepository
import io.reactivex.Completable
import javax.inject.Inject

class SetUnits @Inject constructor(
    private val repository: UnitsRepository
) : CompletableUseCase<SetUnits.Params>() {
    override fun execute(params: Params): Completable {
        return repository.setUnits(params.units)
    }

    data class Params(val units: UnitsModel)
}