package com.stokeapp.stoke.data.repository

import android.content.SharedPreferences
import com.stokeapp.stoke.domain.repository.RegistrationRepository
import javax.inject.Inject

class DefaultRegistrationRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : RegistrationRepository {
        override fun hasShownOnboarding(): Boolean {
        return sharedPreferences.getBoolean(PREF_HAS_SHOW_ONBOARDING, false)
    }

    override fun setHasShownOnboarding(hasShown: Boolean) {
        sharedPreferences.edit()
                .putBoolean(PREF_HAS_SHOW_ONBOARDING, hasShown)
                .commit()
    }

    companion object {
        private const val PREF_HAS_SHOW_ONBOARDING = "pref:has_shown_onboarding"
    }
}