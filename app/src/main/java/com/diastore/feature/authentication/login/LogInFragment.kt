package com.diastore.feature.authentication.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.diastore.DiaStoreActivity
import com.diastore.LoginBinding
import com.diastore.R
import com.diastore.util.BaseFragment
import com.diastore.util.SharedPreferencesManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.UUID


class LogInFragment : BaseFragment<LoginBinding, LoginViewModel>(R.layout.fragment_login) {
    override val viewModel by viewModel<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogin.setOnClickListener {
            SharedPreferencesManager(activity as DiaStoreActivity).saveUser(
                UUID.randomUUID().toString(),
                "John",
                "Doe"
            )
            findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToMainNavigation())
        }

        viewModel.user.observe(viewLifecycleOwner, Observer {
            Log.d("WRKRUser", it.toString())
            //TODO: Save user data that is coming as a responsne from the server after login
            findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToMainNavigation())
        })

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}