package com.stokeapp.stoke.domain.repository

import io.reactivex.Completable
import io.reactivex.Single

interface LocationRepository {
    fun readLocation(): Single<String>
    fun writeLocation(locationName: String): Completable
}