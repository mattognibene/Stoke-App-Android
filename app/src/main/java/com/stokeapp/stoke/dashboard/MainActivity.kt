package com.stokeapp.stoke.dashboard

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.jakewharton.rxrelay2.PublishRelay
import com.stokeapp.stoke.R
import com.stokeapp.stoke.common.BaseActivity
import com.stokeapp.stoke.domain.model.SurfReportModel
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
        refresh()
    }

    private fun refresh() {
        showLoading()
        actions.accept(Action.GetWeatherData("4500546")) // TODO allow choosing location
        actions.accept(Action.GetSurfReport("390"))
    }

    override fun accept(state: State) {
        when (state) {
            is State.GetWeatherDataSuccess -> {
                viewModel.networkSemaphore--
                showWeatherData(state.data)
            }
            is State.GeteatherDataFailure -> {
                viewModel.networkSemaphore--
                TODO()
            }
            is State.GetSurfReportSuccess -> {
                viewModel.networkSemaphore--
                showSurfReport(state.report)
            }
            is State.GetSurfReportFailure -> {
                viewModel.networkSemaphore--
                TODO()
            }
        }.exhaustive

        if (viewModel.networkSemaphore == 0) {
            showScreen()
        }
    }

    private fun showScreen() {
        stokeScoreCardView.visibility = View.VISIBLE
        surfReportCardView.visibility = View.VISIBLE
        weatherScoreCardView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    private fun showLoading() {
        stokeScoreCardView.visibility = View.GONE
        surfReportCardView.visibility = View.GONE
        weatherScoreCardView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun showWeatherData(data: WeatherDataModel) {
        showTemperature(data.tempInKelvin)
        showMainDescription(data.mainDescription) // TODO use longer description
        showHumidity(data.humidityPercentage)
        showWind(data.windSpeed)
        ScoreGenerator.weatherData = data
        score.text = String.format("%.1f", ScoreGenerator.generateScore())
    }

    // Weather
    private fun showHumidity(humidity: Float) {
        val formattedHumidity = String.format("%.0f", humidity)
        humidityText.text = getString(R.string.humidity_1_s, formattedHumidity)
    }

    private fun showTemperature(temp: Float) {
        val fahrenheit = TemperatureConverter.kelvinToFarenheit(temp)
        val formattedTemp = String.format("%.0f", fahrenheit)
        temperatureText.text = getString(R.string.temperature_1s, formattedTemp)
        // TODO format in units
    }

    private fun showWind(wind: Float) {
        windText.text = getString(R.string.wind_speed, wind.toString(), "m/s") // TODO units
    }

    private fun showMainDescription(desc: String) {
        mainDescriptionText.text = desc
    }

    // Surf
    private fun showSurfReport(report: SurfReportModel) {
        showSwellHeight(report.minBreakingHeight, report.maxBreakingHeight, report.unit)
        ScoreGenerator.surfReport = report
        score.text = String.format("%.1f", ScoreGenerator.generateScore())
    }

    private fun showSwellHeight(minBreakingHeight: Float, maxBreakingHeight: Float, units: String) {
        swellHeightText.text = getString(R.string.swell_height,
                minBreakingHeight.toString(),
                maxBreakingHeight.toString(),
                units)
    }
}