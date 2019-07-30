package com.stokeapp.stoke.domain.interactor.registration

import com.stokeapp.stoke.domain.interactor.BaseUseCase
import com.stokeapp.stoke.domain.repository.RegistrationRepository
import javax.inject.Inject

class HasShownOnboarding @Inject constructor(
    private val repository: RegistrationRepository
) : BaseUseCase<Unit, Boolean>() {
    override fun execute(params: Unit): Boolean {
        return repository.hasShownOnboarding()
    }
}