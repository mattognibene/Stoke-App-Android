package com.stokeapp.stoke.data.repository

import android.content.SharedPreferences
import com.stokeapp.stoke.domain.model.TemperatureUnits
import com.stokeapp.stoke.domain.model.UnitsModel
import com.stokeapp.stoke.domain.model.WindUnits
import com.stokeapp.stoke.domain.repository.UnitsRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class DefaultUnitsRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : UnitsRepository {
    override fun getUnits(): Single<UnitsModel> {
        val temperature = sharedPreferences.getString(PREFERENCE_TEMPERATURE_UNITS, null)
        val wind = sharedPreferences.getString(PREFERENCE_WIND_UNITS, null)
        return if (temperature != null && wind != null) {
            Single.just(UnitsModel(
                    windUnits = WindUnits.valueOf(wind),
                    temperatureUnits = TemperatureUnits.valueOf(temperature)))
        } else {
            Single.just(UnitsModel.default())
        }
    }

    override fun setUnits(units: UnitsModel): Completable {
        val edit = sharedPreferences.edit()
        edit.putString(PREFERENCE_WIND_UNITS, units.windUnits.name)
        edit.putString(PREFERENCE_TEMPERATURE_UNITS, units.temperatureUnits.name)
        edit.commit()
        return Completable.complete()
    }

    companion object {
        private const val PREFERENCE_TEMPERATURE_UNITS = "pref:temp_units"
        private const val PREFERENCE_WIND_UNITS = "pref:wind_units"
    }
}