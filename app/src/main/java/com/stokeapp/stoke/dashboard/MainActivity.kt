package com.stokeapp.stoke.dashboard

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.jakewharton.rxrelay2.PublishRelay
import com.stokeapp.stoke.R
import com.stokeapp.stoke.common.BaseActivity
import com.stokeapp.stoke.domain.model.WeatherDataModel
import com.stokeapp.stoke.score.ScoreGenerator
import com.stokeapp.stoke.util.TemperatureConverter
import com.stokeapp.stoke.util.exhaustive
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), Consumer<State> {

    override fun layoutId(): Int = R.layout.activity_main

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
    }

    override fun onResume() {
        super.onResume()
        actions.accept(Action.GetWeatherData("4500546")) // TODO allow
    }

    override fun accept(state: State) {
        when (state) {
            is State.GetWeatherDataSuccess -> showData(state.data)
            is State.GeteatherDataFailure -> TODO()
        }.exhaustive
    }

    private fun showData(data: WeatherDataModel) {
        showTemperature(data.tempInKelvin)
        showMainDescription(data.mainDescription, data.conditionCode)
        score.text = String.format("%.1f", ScoreGenerator.generateScore())
    }

    private fun showTemperature(temp: Float) {
        val fahrenheit = TemperatureConverter.kelvinToFarenheit(temp)
        val formattedTemp = String.format("%.0f", fahrenheit)
        temperatureText.text = getString(R.string.temperature_1s, formattedTemp)
        // TODO format in units
        ScoreGenerator.tempInF = fahrenheit
    }

    private fun showMainDescription(desc: String, code: String) {
        mainDescriptionText.text = desc
        ScoreGenerator.conditionCode = code
    }
}