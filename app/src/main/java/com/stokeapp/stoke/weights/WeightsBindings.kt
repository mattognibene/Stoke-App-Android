package com.stokeapp.stoke.weights

import androidx.lifecycle.ViewModel
import com.stoke.stokeapp.common.android.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class WeightsBindings {
    @Binds
    @IntoMap
    @ViewModelKey(WeightsViewModel::class)
    abstract fun bindViewModel(viewModel: WeightsViewModel): ViewModel
}