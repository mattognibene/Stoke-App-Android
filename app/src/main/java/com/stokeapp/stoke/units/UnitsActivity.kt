package com.stokeapp.stoke.units

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.jakewharton.rxrelay2.PublishRelay
import com.stokeapp.stoke.R
import com.stokeapp.stoke.common.BaseActivity
import com.stokeapp.stoke.util.exhaustive
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_units.*
import timber.log.Timber
import javax.inject.Inject

class UnitsActivity : BaseActivity(), Consumer<State> {

    @Inject lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: UnitsViewModel
    private val actions = PublishRelay.create<Action>()

    override fun layoutId(): Int = R.layout.activity_units

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory)[UnitsViewModel::class.java]
        actions.compose(viewModel.model())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(lifecycle.scope())
                .subscribe(this)

        initUi()
    }

    override fun onResume() {
        super.onResume()
        actions.accept(Action.GetUnits)
    }

    private fun initUi() {
        unitsToolbar.setNavigationOnClickListener { onBackPressed() }
        btnSave.setOnClickListener {
            actions.accept(Action.SetUnits(
                    temperatureName = temperatureSpinner.selectedItem.toString(),
                    windName = windSpinner.selectedItem.toString()
            ))
        }
        ArrayAdapter.createFromResource(
                this,
                R.array.temperature_units,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            temperatureSpinner.adapter = adapter
        }
        ArrayAdapter.createFromResource(
                this,
                R.array.wind_units,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            windSpinner.adapter = adapter
        }
    }

    private fun initUnits(state: State.GetUnitsSuccess) {
        val windArray = resources.getStringArray(R.array.wind_units)
        val tempArray = resources.getStringArray(R.array.temperature_units)
        windSpinner.setSelection(windArray.indexOf(state.windName))
        temperatureSpinner.setSelection(tempArray.indexOf(state.temperatureName))

        val onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                btnSave.isEnabled = true
            }
        }
        windSpinner.onItemSelectedListener = onItemSelectedListener
        temperatureSpinner.onItemSelectedListener = onItemSelectedListener
        btnSave.isEnabled = false
    }

    private fun showLoading() {
        unitsInstructions.isVisible = false
        temperatureSpinner.isVisible = false
        windSpinner.isVisible = false
        progressBar.isVisible = true
    }

    private fun showScreen() {
        unitsInstructions.isVisible = true
        temperatureSpinner.isVisible = true
        windSpinner.isVisible = true
        progressBar.isVisible = false
    }

    override fun accept(state: State) {
        when (state) {
            is State.Loading -> showLoading()
            is State.GetUnitsSuccess -> {
                initUnits(state)
                showScreen()
            }
            is State.GetUnitsFailure -> {
                Timber.e(state.error) // TODO escape somehow
            }
            State.SetUnitsSuccess -> {
                finish()
            }
            is State.SetUnitsFailure -> {
                Timber.e(state.error)
                finish()
            }
        }.exhaustive
    }

    companion object {
        fun launch(
            activity: Activity
        ) {
            val intent = Intent(activity, UnitsActivity::class.java)
            activity.startActivity(intent)
        }
    }
}