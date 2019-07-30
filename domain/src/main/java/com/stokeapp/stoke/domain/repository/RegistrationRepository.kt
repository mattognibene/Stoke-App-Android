package com.stokeapp.stoke.domain.repository

interface RegistrationRepository {
    fun hasShownOnboarding(): Boolean
    fun setHasShownOnboarding(hasShown: Boolean)
}