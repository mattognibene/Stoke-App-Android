package com.stokeapp.stoke.splash

import android.os.Bundle
import com.stokeapp.stoke.Navigator
import com.stokeapp.stoke.R
import com.stokeapp.stoke.common.BaseActivity
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    @Inject lateinit var navigator: Navigator
    override fun layoutId(): Int = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        navigator.goToNext(this)
    }
}