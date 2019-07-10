package com.stokeapp.stoke.location

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxrelay2.PublishRelay
import com.stokeapp.stoke.R
import com.stokeapp.stoke.common.BaseActivity
import com.stokeapp.stoke.dashboard.Location
import com.stokeapp.stoke.util.exhaustive
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_location.*
import javax.inject.Inject

class LocationActivity : BaseActivity(), Consumer<State> {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: LocationViewModel
    private val actions = PublishRelay.create<Action>()

    private val adapter = LocationAdapter { item ->
        val locationName = item.name
        val intent = Intent().apply { putExtra(EXTRA_LOCATION, locationName) }
        setResult(Activity.RESULT_OK, intent)
        actions.accept(Action.WriteLocation(locationName))
    }

    override fun layoutId(): Int = R.layout.activity_location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory)[LocationViewModel::class.java]

        actions.compose(viewModel.model())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(lifecycle.scope())
                .subscribe(this)
        initUi()
        setUpRecyclerView()
    }

    private fun initUi() {
        toolbar.setNavigationOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun setUpRecyclerView() {
        locationRecyclerView.layoutManager = LinearLayoutManager(this)
        locationRecyclerView.addItemDecoration(
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        locationRecyclerView.adapter = adapter
        val items = Location.values().map { location ->
            LocationItem(name = location.name, location = location.location)
        }
        adapter.submitList(items)
    }

    override fun accept(state: State) {
        when (state) {
            State.WriteLocationSuccess -> { finish() }
            is State.WriteLocationFailure -> TODO()
        }.exhaustive
    }

    companion object {
        const val REQUEST_SELECT_LOCATION = 0
        const val EXTRA_LOCATION = "extra:location"

        fun launchForResult(activity: Activity) {
            val intent = Intent(activity, LocationActivity::class.java)
            activity.startActivityForResult(intent, REQUEST_SELECT_LOCATION)
        }

        fun launch(activity: Activity) {
            val intent = Intent(activity, LocationActivity::class.java)
            activity.startActivity(intent)
        }
    }
}