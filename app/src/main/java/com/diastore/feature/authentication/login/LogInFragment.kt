package com.diastore.feature.authentication.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.diastore.DiaStoreActivity
import com.diastore.LoginBinding
import com.diastore.R
import com.diastore.model.User
import com.diastore.util.BaseFragment
import com.diastore.util.SharedPreferencesManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.UUID


class LogInFragment : BaseFragment<LoginBinding, LoginViewModel>(R.layout.fragment_login) {
    override val viewModel by viewModel<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogin.setOnClickListener {
//            viewModel.login()
            SharedPreferencesManager(activity as DiaStoreActivity).saveCurrentUser(
                User(
                    UUID.randomUUID(),
                    "John",
                    "Doe",
                    "john.doe@mail.com",
                    "Password1",
                    170,
                    58,
                    15,
                    50,
                    24
                )
            )
            findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToMainNavigation())
        }

        viewModel.user.observe(viewLifecycleOwner, Observer {
            Log.d("WRKRUser", it.toString())
            SharedPreferencesManager(requireActivity() as DiaStoreActivity).saveCurrentUser(it)
            findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToMainNavigation())
        })

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}