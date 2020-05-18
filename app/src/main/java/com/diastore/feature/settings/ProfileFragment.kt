package com.diastore.feature.settings

import android.os.Bundle
import android.view.View
import com.diastore.ProfileBinding
import com.diastore.R
import com.diastore.util.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<ProfileBinding, SettingsViewModel>(R.layout.fragment_profile) {
    override val viewModel by viewModel<SettingsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}