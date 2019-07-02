package com.stokeapp.stoke.location

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.stokeapp.stoke.R
import com.stokeapp.stoke.common.BaseActivity
import kotlinx.android.synthetic.main.activity_location.*

class LocationActivity : BaseActivity() {

    private val adapter = LocationAdapter()

    override fun layoutId(): Int = R.layout.activity_location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
        setUpRecyclerView()
        adapter.submitList(listOf(LocationItem("Atlantic City, NJ"),
                LocationItem("San Diego, CA")))
    }

    private fun initUi() {
        toolbar.setNavigationOnClickListener { setResultAndExit(Activity.RESULT_CANCELED) }
    }

    private fun setUpRecyclerView() {
        locationRecyclerView.layoutManager = LinearLayoutManager(this)
        locationRecyclerView.adapter = adapter
    }

    private fun setResultAndExit(resultCode: Int) {
        setResult(resultCode)
        onBackPressed()
    }

    companion object {
        const val REQUEST_SELECT_LOCATION = 0

        fun launchForResult(activity: Activity) {
            val intent = Intent(activity, LocationActivity::class.java)
            activity.startActivityForResult(intent, REQUEST_SELECT_LOCATION)
        }
    }
}