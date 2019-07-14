package com.stokeapp.stoke.domain.repository

import com.stokeapp.stoke.domain.model.UnitsModel
import io.reactivex.Completable
import io.reactivex.Single

interface UnitsRepository {
    fun getUnits(): Single<UnitsModel>
    fun setUnits(units: UnitsModel): Completable
}