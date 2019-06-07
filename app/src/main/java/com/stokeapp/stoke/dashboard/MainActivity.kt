package com.stokeapp.stoke.dashboard

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.jakewharton.rxrelay2.PublishRelay
import com.stokeapp.stoke.R
import com.stokeapp.stoke.common.BaseActivity
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), Consumer<State> {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: MainViewModel
    private val actions = PublishRelay.create<Action>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory)[MainViewModel::class.java]

        actions.compose(viewModel.model())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(lifecycle.scope())
                .subscribe(this)

        actions.accept(Action.GetTemperature("London"))
    }

    override fun layoutId(): Int = R.layout.activity_main

    override fun accept(state: State) {
        when (state) {
            is State.GetTemperatureSuccess -> {
                temperatureText.text = getString(R.string.temperature_1s, state.celsius)
            }
            is State.GetTemeperatureFailure -> {
                // TODO handle error
            }
        }
    }
}