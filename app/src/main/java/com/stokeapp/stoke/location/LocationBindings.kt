package com.stokeapp.stoke.location

import androidx.lifecycle.ViewModel
import com.stoke.stokeapp.common.android.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class LocationBindings {
    @Binds
    @IntoMap
    @ViewModelKey(LocationViewModel::class)
    abstract fun bindViewModel(viewModel: LocationViewModel): ViewModel
}