package com.stokeapp.stoke.data.injection

import com.stokeapp.stoke.data.repository.DefaultLocationRepository
import com.stokeapp.stoke.data.repository.DefaultRegistrationRepository
import com.stokeapp.stoke.data.repository.DefaultSurfReportRepository
import com.stokeapp.stoke.data.repository.DefaultUnitsRepository
import com.stokeapp.stoke.data.repository.DefaultWeatherRepository
import com.stokeapp.stoke.data.repository.DefaultWeightsRepository
import com.stokeapp.stoke.domain.repository.LocationRepository
import com.stokeapp.stoke.domain.repository.RegistrationRepository
import com.stokeapp.stoke.domain.repository.SurfReportRepository
import com.stokeapp.stoke.domain.repository.UnitsRepository
import com.stokeapp.stoke.domain.repository.WeatherRepository
import com.stokeapp.stoke.domain.repository.WeightsRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataBindings {

    @Binds
    abstract fun bindWeatherRepository(
        repository: DefaultWeatherRepository
    ): WeatherRepository

    @Binds
    abstract fun bindSurfReportRepository(
        repository: DefaultSurfReportRepository
    ): SurfReportRepository

    @Binds
    abstract fun bindLocationRepository(
        repository: DefaultLocationRepository
    ): LocationRepository

    @Binds
    abstract fun bindWeightsRepository(
        repository: DefaultWeightsRepository
    ): WeightsRepository

    @Binds
    abstract fun bindUnitsRepository(
        repository: DefaultUnitsRepository
    ): UnitsRepository

    @Binds
    abstract fun bindsRegistrationRepository(
        repository: DefaultRegistrationRepository
    ): RegistrationRepository
}