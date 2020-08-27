package com.diastore.feature.authentication.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.diastore.LoginBinding
import com.diastore.R
import com.diastore.shared.DiaStoreBiometricPrompt
import com.diastore.util.BaseFragment
import com.diastore.util.SharedPreferencesManager
import com.diastore.util.extensions.encryptUser
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class LogInFragment : BaseFragment<LoginBinding, LoginViewModel>(R.layout.fragment_login) {
    override val viewModel by viewModel<LoginViewModel>()
    private val sharedPreferencesManager by inject<SharedPreferencesManager>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogin.setOnClickListener {
            viewModel.login()
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.user.observe(viewLifecycleOwner, Observer {
            showAndEncrypt()
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), getString(R.string.error_message), Toast.LENGTH_SHORT).show()
        })

    }

    private fun showAndEncrypt() {
        DiaStoreBiometricPrompt(requireContext(), this, requireContext().mainExecutor) { _ ->
            viewModel.user.value?.let {
                val encryptedUser = it.encryptUser()
                viewModel.saveEncryptedUser(encryptedUser)
                sharedPreferencesManager.saveUser(
                    it.id.toString(),
                    it.firstName,
                    it.lastName
                )
                findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToMainNavigation())
            }
        }.authenticate()
    }
}