package com.stokeapp.stoke.dashboard

import androidx.lifecycle.ViewModel
import com.stoke.stokeapp.common.android.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DashboardBindings {
    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    abstract fun bindViewModel(viewModel: DashboardViewModel): ViewModel
}