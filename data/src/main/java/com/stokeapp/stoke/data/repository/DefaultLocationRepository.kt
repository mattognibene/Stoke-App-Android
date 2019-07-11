package com.stokeapp.stoke.data.repository

import android.content.SharedPreferences
import com.stokeapp.stoke.domain.repository.LocationRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class DefaultLocationRepository @Inject constructor(
    private val preference: SharedPreferences
) : LocationRepository {

    override fun readLocation(): Single<String> {
        val location = preference.getString(LOCATION_PREFERENCE, null)
        return if (location != null) {
            Single.just(location)
        } else {
            Single.error<String>(Throwable("Location Preference not found"))
        }
    }

    override fun writeLocation(locationName: String): Completable {
        preference
                .edit()
                .putString(LOCATION_PREFERENCE, locationName)
                .commit()
        return Completable.complete()
    }

    companion object {
        private const val LOCATION_PREFERENCE = "preference:location"
    }
}