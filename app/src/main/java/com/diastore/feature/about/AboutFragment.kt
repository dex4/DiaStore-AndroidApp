package com.diastore.feature.about

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.diastore.R
import com.diastore.databinding.FragmentAboutBinding
import com.diastore.util.ViewBindingFragment

class AboutFragment : ViewBindingFragment<FragmentAboutBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewBinding(FragmentAboutBinding.inflate(layoutInflater))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.buttonTermsAndConditions.setOnClickListener {
            Toast.makeText(context, getString(R.string.about_terms_and_conditions_title), Toast.LENGTH_SHORT).show()
        }

        binding.buttonPrivacyPolicy.setOnClickListener {
            Toast.makeText(context, getString(R.string.about_privacy_policy), Toast.LENGTH_SHORT).show()
        }

        binding.buttonFacebook.setOnClickListener {
            Toast.makeText(context, "Facebook", Toast.LENGTH_SHORT).show()
        }

        binding.buttonTwitter.setOnClickListener {
            Toast.makeText(context, "Twitter", Toast.LENGTH_SHORT).show()
        }

        binding.buttonInstagram.setOnClickListener {
            Toast.makeText(context, "Instagram", Toast.LENGTH_SHORT).show()
        }

    }
}