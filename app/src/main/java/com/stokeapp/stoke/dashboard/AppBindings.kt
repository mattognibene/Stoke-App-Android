package com.stokeapp.stoke.dashboard

import androidx.lifecycle.ViewModel
import com.stoke.stokeapp.common.android.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class AppBindings {
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindViewModel(viewModel: MainViewModel): ViewModel
}