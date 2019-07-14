package com.stokeapp.stoke.weights

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.jakewharton.rxrelay2.PublishRelay
import com.stokeapp.stoke.R
import com.stokeapp.stoke.common.BaseActivity
import com.stokeapp.stoke.domain.model.WeightsModel
import com.stokeapp.stoke.util.exhaustive
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_weights.*
import timber.log.Timber
import javax.inject.Inject

class WeightsActivity : BaseActivity(), Consumer<State> {

    @Inject lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: WeightsViewModel
    private val actions = PublishRelay.create<Action>()

    override fun layoutId(): Int = R.layout.activity_weights

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory)[WeightsViewModel::class.java]
        actions.compose(viewModel.model())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(lifecycle.scope())
                .subscribe(this)

        initUi()
    }

    override fun onResume() {
        super.onResume()
        actions.accept(Action.GetWeights)
    }

    private fun initUi() {
        val changedListener = object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                btnSave.isEnabled = true
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        }
        surfReportSlider.setOnSeekBarChangeListener(changedListener)
        weatherSlider.setOnSeekBarChangeListener(changedListener)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        btnSave.setOnClickListener {
            val surfWeight = surfReportSlider.progress
            val weatherWeight = weatherSlider.progress
            actions.accept(Action.SetWeights(
                    surfWeight = surfWeight.toFloat() / 100,
                    weatherWeight = weatherWeight.toFloat() / 100))
        }
    }

    private fun initWeights(weights: WeightsModel) {
        surfReportSlider.progress = weights.surfWeight.toInt()
        weatherSlider.progress = weights.weatherWeight.toInt()
        btnSave.isEnabled = false
    }

    private fun showLoading() {
        instructions.isVisible = false
        surfReportText.isVisible = false
        weatherText.isVisible = false
        weatherSlider.isVisible = false
        surfReportSlider.isVisible = false
        progressBar.isVisible = true
    }

    private fun showScreen() {
        instructions.isVisible = true
        surfReportText.isVisible = true
        weatherText.isVisible = true
        weatherSlider.isVisible = true
        surfReportSlider.isVisible = true
        progressBar.isVisible = false
    }

    override fun accept(state: State) {
        when (state) {
            is State.Loading -> showLoading()
            is State.GetWeightsSuccess -> {
                initWeights(state.weights)
                showScreen()
            }
            is State.GetWeightsFailure -> {
                Timber.e(state.error) // TODO escape somehow
            }
            State.SetWeightsSuccess -> {
                finish()
            }
            is State.SetWeightsFailure -> {
                Timber.e(state.error)
                finish()
            }
        }.exhaustive
    }

    companion object {
        fun launch(
            activity: Activity
        ) {
            val intent = Intent(activity, WeightsActivity::class.java)
            activity.startActivity(intent)
        }
    }
}