package com.stokeapp.stoke.domain.repository

import com.stokeapp.stoke.domain.model.WeightsModel
import io.reactivex.Completable
import io.reactivex.Single

interface WeightsRepository {
    fun setWeights(weights: WeightsModel): Completable
    fun getWeights(): Single<WeightsModel>
}