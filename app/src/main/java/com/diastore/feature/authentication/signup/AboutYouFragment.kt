package com.diastore.feature.authentication.signup

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.diastore.DiaStoreActivity
import com.diastore.OnBoardingBinding
import com.diastore.R
import com.diastore.util.BaseFragment
import com.diastore.util.SharedPreferencesManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AboutYouFragment : BaseFragment<OnBoardingBinding, SignUpViewModel>(R.layout.fragment_onboarding) {
    override val viewModel by sharedViewModel<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.signUpResponse.observe(viewLifecycleOwner, Observer {
            Log.d("WRKRSignUpSuccess", it.toString())
            SharedPreferencesManager(activity as DiaStoreActivity).saveCurrentUser(it)
            findNavController().navigate(AboutYouFragmentDirections.actionAboutYouFragmentToMainNavigation())
        })
    }
}