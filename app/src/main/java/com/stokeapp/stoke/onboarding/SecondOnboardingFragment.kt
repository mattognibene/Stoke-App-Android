package com.stokeapp.stoke.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stokeapp.stoke.R
import com.stokeapp.stoke.dashboard.DashboardActivity
import com.stokeapp.stoke.domain.interactor.registration.SetHasShownOnboarding
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_onboarding_two.*
import javax.inject.Inject

class SecondOnboardingFragment : DaggerFragment() {
    @Inject lateinit var setHasShownOnboarding: SetHasShownOnboarding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnGetStarted.setOnClickListener {
            DashboardActivity.launchWithAnimation(requireActivity())
            setHasShownOnboarding.invoke(true)
            requireActivity().finish()
        }
    }
}