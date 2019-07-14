package com.stokeapp.stoke.settings

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.stokeapp.stoke.R
import com.stokeapp.stoke.common.BaseActivity
import com.stokeapp.stoke.location.LocationActivity
import kotlinx.android.synthetic.main.activity_settings.*
import android.content.pm.PackageManager
import android.R.attr.versionName
import com.stokeapp.stoke.units.UnitsActivity
import com.stokeapp.stoke.weights.WeightsActivity
import timber.log.Timber

class SettingsActivity : BaseActivity() {
    override fun layoutId(): Int = R.layout.activity_settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
    }

    private fun initUi() {
        settingLocation.setOnClickListener { LocationActivity.launch(this) }
        settingsWeights.setOnClickListener { WeightsActivity.launch(this) }
        settingUnits.setOnClickListener { UnitsActivity.launch(this) }

        toolbar.setNavigationOnClickListener { onBackPressed() }
        try {
            val pInfo = packageManager.getPackageInfo(packageName, 0)
            val version = pInfo.versionName
            appVersion.text = getString(R.string.app_version, version)
        } catch (e: PackageManager.NameNotFoundException) {
            Timber.e(e)
        }
    }

    companion object {
        fun launch(
            activity: Activity
        ) {
            val intent = Intent(activity, SettingsActivity::class.java)
            activity.startActivity(intent)
        }
    }
}