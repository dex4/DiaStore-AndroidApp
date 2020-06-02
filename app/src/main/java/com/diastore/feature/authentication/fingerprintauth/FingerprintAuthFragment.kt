package com.diastore.feature.authentication.fingerprintauth

import android.os.Bundle
import android.view.View
import com.diastore.FingerprintAuthBinding
import com.diastore.R
import com.diastore.util.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class FingerprintAuthFragment : BaseFragment<FingerprintAuthBinding, FingerprintAuthViewModel>(R.layout.fragment_fingerprint_auth) {
    override val viewModel by viewModel<FingerprintAuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonEncrypt.setOnClickListener {

        }
    }


}