package com.stokeapp.stoke.location

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.stokeapp.stoke.R
import com.stokeapp.stoke.common.BaseActivity
import com.stokeapp.stoke.dashboard.Location
import kotlinx.android.synthetic.main.activity_location.*

class LocationActivity : BaseActivity() {

    private val adapter = LocationAdapter { item ->
        val locationName = item.name
        val intent = Intent().apply { putExtra(EXTRA_LOCATION, locationName) }
        setResultAndExit(Activity.RESULT_OK, intent)
    }

    override fun layoutId(): Int = R.layout.activity_location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
        setUpRecyclerView()
    }

    private fun initUi() {
        toolbar.setNavigationOnClickListener { setResultAndExit(Activity.RESULT_CANCELED) }
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

    private fun setResultAndExit(
        resultCode: Int,
        data: Intent? = null
    ) {
        setResult(resultCode, data)
        onBackPressed()
    }

    companion object {
        const val REQUEST_SELECT_LOCATION = 0
        const val EXTRA_LOCATION = "extra:location"

        fun launchForResult(activity: Activity) {
            val intent = Intent(activity, LocationActivity::class.java)
            activity.startActivityForResult(intent, REQUEST_SELECT_LOCATION)
        }
    }
}