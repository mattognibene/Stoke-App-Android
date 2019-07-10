package com.stokeapp.stoke.domain.interactor.location

import com.stokeapp.stoke.domain.interactor.CompletableUseCase
import com.stokeapp.stoke.domain.repository.LocationRepository
import io.reactivex.Completable
import javax.inject.Inject

class WriteLocation @Inject constructor(
    private val locationRepository: LocationRepository
) : CompletableUseCase<WriteLocation.Params>() {

    override fun execute(params: WriteLocation.Params): Completable {
        return locationRepository.writeLocation(params.locationName)
    }

    data class Params(val locationName: String)
}