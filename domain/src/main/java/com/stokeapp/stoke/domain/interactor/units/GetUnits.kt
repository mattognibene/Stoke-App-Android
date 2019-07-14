package com.stokeapp.stoke.domain.interactor.units

import com.stokeapp.stoke.domain.interactor.SingleUseCase
import com.stokeapp.stoke.domain.model.UnitsModel
import com.stokeapp.stoke.domain.repository.UnitsRepository
import io.reactivex.Single
import javax.inject.Inject

class GetUnits @Inject constructor(
    private val repository: UnitsRepository
) : SingleUseCase<Unit, UnitsModel>() {
    override fun execute(params: Unit): Single<UnitsModel> {
        return repository.getUnits()
                .onErrorReturn { UnitsModel.default() }
    }
}