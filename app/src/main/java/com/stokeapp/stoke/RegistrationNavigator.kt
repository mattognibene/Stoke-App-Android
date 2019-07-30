package com.stokeapp.stoke

import android.app.Activity
import com.stokeapp.stoke.dashboard.DashboardActivity
import com.stokeapp.stoke.domain.interactor.registration.HasShownOnboarding
import com.stokeapp.stoke.onboarding.OnboardingActivity
import javax.inject.Inject

class RegistrationNavigator @Inject constructor(
    private val hasShownOnboarding: HasShownOnboarding
) : Navigator() {
    override fun goToNext(activity: Activity) {
        if (!hasShownOnboarding.invoke(Unit)) {
            useAndFinish(activity) { OnboardingActivity.launch(activity) }
            return
        }

        DashboardActivity.launch(activity)
        activity.finish()
    }
}