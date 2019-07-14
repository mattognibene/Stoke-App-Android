package com.stokeapp.stoke

import android.app.Application
import android.content.Context
import com.stokeapp.stoke.dashboard.DashboardBindings
import com.stokeapp.stoke.dashboard.DashboardActivity
import com.stokeapp.stoke.location.LocationActivity
import com.stokeapp.stoke.location.LocationBindings
import com.stokeapp.stoke.settings.SettingsActivity
import com.stokeapp.stoke.units.UnitsActivity
import com.stokeapp.stoke.units.UnitsBindings
import com.stokeapp.stoke.weights.WeightsActivity
import com.stokeapp.stoke.weights.WeightsBindings
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppBindings {
    @ContributesAndroidInjector(modules = [DashboardBindings::class])
    abstract fun mainActivity(): DashboardActivity

    @ContributesAndroidInjector(modules = [LocationBindings::class])
    abstract fun locationActivity(): LocationActivity

    @ContributesAndroidInjector
    abstract fun settingsActivity(): SettingsActivity

    @ContributesAndroidInjector(modules = [WeightsBindings::class])
    abstract fun weightsActivity(): WeightsActivity

    @ContributesAndroidInjector(modules = [UnitsBindings::class])
    abstract fun unitsActivity(): UnitsActivity

    @Binds
    abstract fun bindContext(app: Application): Context
}