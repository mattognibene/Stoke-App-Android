package com.stokeapp.stoke.domain.interactor.registration

import com.stokeapp.stoke.domain.interactor.BaseUseCase
import com.stokeapp.stoke.domain.repository.RegistrationRepository
import javax.inject.Inject

class SetHasShownOnboarding @Inject constructor(
    private val repository: RegistrationRepository
) : BaseUseCase<Boolean, Unit>() {
    override fun execute(params: Boolean) {
        repository.setHasShownOnboarding(params)
    }
}