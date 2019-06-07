package com.stokeapp.stoke

import androidx.lifecycle.ViewModelProvider
import com.stokeapp.stoke.common.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelBindings {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}