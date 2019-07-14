package com.stokeapp.stoke.units

import androidx.lifecycle.ViewModel
import com.stoke.stokeapp.common.android.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class UnitsBindings {
    @Binds
    @IntoMap
    @ViewModelKey(UnitsViewModel::class)
    abstract fun bindViewModel(viewModel: UnitsViewModel): ViewModel
}