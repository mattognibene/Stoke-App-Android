package com.stokeapp.stoke.dashboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.jakewharton.rxrelay2.PublishRelay
import com.stokeapp.stoke.R
import com.stokeapp.stoke.common.BaseActivity
import com.stokeapp.stoke.domain.model.SurfReportModel
import com.stokeapp.stoke.domain.model.WeatherDataModel
import com.stokeapp.stoke.location.LocationActivity
import com.stokeapp.stoke.score.ScoreGenerator
import com.stokeapp.stoke.settings.SettingsActivity
import com.stokeapp.stoke.util.TemperatureConverter
import com.stokeapp.stoke.util.exhaustive
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

class DashboardActivity : BaseActivity(), Consumer<State> {

    override fun layoutId(): Int = R.layout.activity_main

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: DashboardViewModel
    private val actions = PublishRelay.create<Action>()

    private var location: Location = Location.ATLANTIC_CITY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory)[DashboardViewModel::class.java]

        actions.compose(viewModel.model())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(lifecycle.scope())
                .subscribe(this)
        initUi()
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            LocationActivity.REQUEST_SELECT_LOCATION -> {
                if (resultCode == Activity.RESULT_OK) {
                    handleChangeLocation(data?.getStringExtra(LocationActivity.EXTRA_LOCATION))
                }
            }
        }
        refresh()
    }

    private fun refresh() {
        if (!swipeRefresh.isRefreshing) {
            showLoading()
        }
        actions.accept(Action.GetLocation)
        swipeRefresh.isRefreshing = false
    }

    private fun initUi() {
        swipeRefresh.setOnRefreshListener {
            refresh()
        }
        editLocation.setOnClickListener {
            LocationActivity.launchForResult(this)
        }
        locationText.setOnClickListener {
            LocationActivity.launchForResult(this)
        }
        btnSettings.setOnClickListener {
            SettingsActivity.launch(this)
        }
    }

    private fun initLocation(name: String) {
        val location = Location.getLocation(name)
        location?.let { loc ->
            this.location = loc
            locationText.text = loc.location
        }
    }

    private fun handleChangeLocation(locationName: String?) {
        locationName?.let { name -> initLocation(name) }
    }

    override fun accept(state: State) {
        when (state) {
            is State.Loading -> showLoading()
            is State.GetCombinedDataSuccess -> {
                showSurfReport(state.data.surfData)
                showWeatherData(state.data.weatherData)
                showScreen()
            }
            is State.GetCombinedDataFailure -> {
                Timber.e(state.e)
                // TODO handle
            }
            is State.GetLocationSuccess -> {
                initLocation(state.locationName)
                actions.accept(Action.GetCombinedData(location = location))
            }
            is State.GetLocationFailure -> {
                Timber.e(state.e)
                initLocation(Location.ATLANTIC_CITY.name)
                actions.accept(Action.GetCombinedData(location = location))
            }
        }.exhaustive
    }

    private fun showScreen() {
        score.text = String.format("%.1f", ScoreGenerator.generateScore())
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
        showSwellScore(report.solidRating, report.fadedRating)
        ScoreGenerator.surfReport = report
    }

    private fun showSwellScore(solidRating: Int, fadedRating: Int) {
        swellRatingContainer.removeAllViews()
        Timber.d("Solid Rating: $solidRating Faded Rating: $fadedRating")
        for (i in 1..solidRating) {
            val imageView = ImageView(this)
            imageView.setImageResource(R.drawable.ic_star)
            swellRatingContainer.addView(imageView)
        }

        for (i in 1..fadedRating) {
            val imageView = ImageView(this)
            imageView.setImageResource(R.drawable.ic_star_border)
            swellRatingContainer.addView(imageView)
        }
    }

    private fun showSwellHeight(minBreakingHeight: Float, maxBreakingHeight: Float, units: String) {
        swellHeightText.text = getString(R.string.swell_height,
                minBreakingHeight.toString(),
                maxBreakingHeight.toString(),
                units)
    }
}