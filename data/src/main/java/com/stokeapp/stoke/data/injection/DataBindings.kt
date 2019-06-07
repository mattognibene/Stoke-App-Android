package com.stokeapp.stoke.data.injection

import com.stokeapp.stoke.data.repository.DefaultWeatherRepository
import com.stokeapp.stoke.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataBindings {

    @Binds
    abstract fun bindWeatherRepository(
        repository: DefaultWeatherRepository
    ): WeatherRepository
}