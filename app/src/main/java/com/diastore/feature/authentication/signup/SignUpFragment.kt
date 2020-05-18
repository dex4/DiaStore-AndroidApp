package com.diastore.feature.authentication.signup

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.diastore.R
import com.diastore.SignUpBinding
import com.diastore.util.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SignUpFragment : BaseFragment<SignUpBinding, SignUpViewModel>(R.layout.fragment_signup) {
    override val viewModel by sharedViewModel<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}