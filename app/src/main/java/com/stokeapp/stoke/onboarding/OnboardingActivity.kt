package com.stokeapp.stoke.onboarding

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.stokeapp.stoke.R
import com.stokeapp.stoke.common.BaseActivity
import kotlinx.android.synthetic.main.activity_onboarding.*

class OnboardingActivity : BaseActivity() {
    override fun layoutId(): Int = R.layout.activity_onboarding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        pager.adapter = OnboardingPagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(pager, true)
    }

    class OnboardingPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = Tab.values().size

        override fun getItem(position: Int): Fragment {
            val tab = Tab.values()[position]
            return tab.getItem()
        }

        enum class Tab(val getItem: () -> Fragment) {
            FIRST({ FirstOnboardingFragment() }),
            SECOND({ SecondOnboardingFragment() })
        }
    }

    companion object {
        fun launch(
            activity: Activity
        ) {
            val intent = Intent(activity, OnboardingActivity::class.java)
            activity.startActivity(intent)
        }
    }
}