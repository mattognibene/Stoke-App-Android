package com.stokeapp.stoke.domain.interactor.location

import com.stokeapp.stoke.domain.interactor.SingleUseCase
import com.stokeapp.stoke.domain.repository.LocationRepository
import io.reactivex.Single
import javax.inject.Inject

class GetLocation @Inject constructor(
    private val locationRepository: LocationRepository
) : SingleUseCase<Unit, String>() {
    override fun execute(params: Unit): Single<String> {
        return locationRepository.readLocation()
    }
}