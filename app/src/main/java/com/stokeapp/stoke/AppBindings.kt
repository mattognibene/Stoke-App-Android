package com.stokeapp.stoke

import com.stokeapp.stoke.dashboard.DashboardBindings
import com.stokeapp.stoke.dashboard.DashboardActivity
import com.stokeapp.stoke.location.LocationActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppBindings {
    @ContributesAndroidInjector(modules = [DashboardBindings::class])
    abstract fun mainActivity(): DashboardActivity

    @ContributesAndroidInjector
    abstract fun locationActivity(): LocationActivity
}