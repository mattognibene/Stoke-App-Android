package com.stokeapp.stoke.data.repository

import android.content.SharedPreferences
import com.stokeapp.stoke.domain.model.WeightsModel
import com.stokeapp.stoke.domain.repository.WeightsRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class DefaultWeightsRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : WeightsRepository {
    override fun getWeights(): Single<WeightsModel> {
        val surfWeight = sharedPreferences.getFloat(KEY_SURF_WEIGHT, -1f)
        val weatherWeight = sharedPreferences.getFloat(KEY_WEATHER_WEIGHT, -1f)
        return Single.just(WeightsModel(
                surfWeight = if (surfWeight >= 0) surfWeight else .5f,
                weatherWeight = if (weatherWeight >= 0) weatherWeight else .5f
        ))
    }

    override fun setWeights(weights: WeightsModel): Completable {
        val edit = sharedPreferences.edit()
        edit.putFloat(KEY_SURF_WEIGHT, weights.surfWeight)
        edit.putFloat(KEY_WEATHER_WEIGHT, weights.weatherWeight)
        edit.commit()
        return Completable.complete()
    }

    companion object {
        private const val KEY_SURF_WEIGHT = "preference:surf_weight"
        private const val KEY_WEATHER_WEIGHT = "preference:weather_weight"
    }
}