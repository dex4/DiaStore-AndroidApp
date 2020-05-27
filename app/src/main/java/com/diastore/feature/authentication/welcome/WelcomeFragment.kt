package com.diastore.feature.authentication.welcome

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.diastore.DiaStoreActivity
import com.diastore.R
import com.diastore.WelcomeBinding
import com.diastore.util.DataBindingFragment
import com.diastore.util.SharedPreferencesManager

class WelcomeFragment : DataBindingFragment<WelcomeBinding>(R.layout.fragment_welcome) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (SharedPreferencesManager(activity as DiaStoreActivity).getIsUserLoggedIn()) {
            findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToMainNavigation())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonLogin.setOnClickListener {
            findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToLogInFragment())
        }
        binding.buttonSignUp.setOnClickListener {
            findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToSignUpFragment())
        }
    }
}