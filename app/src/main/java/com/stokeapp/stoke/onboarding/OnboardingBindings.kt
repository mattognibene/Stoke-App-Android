package com.stokeapp.stoke.onboarding

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class OnboardingBindings {
    @ContributesAndroidInjector
    abstract fun firstOnboardingFragment(): FirstOnboardingFragment

    @ContributesAndroidInjector
    abstract fun secondOnboardingFragment(): SecondOnboardingFragment
}