package com.diastore.feature.authentication.signup

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.diastore.OnBoardingBinding
import com.diastore.R
import com.diastore.shared.DiaStoreBiometricPrompt
import com.diastore.util.BaseFragment
import com.diastore.util.SharedPreferencesManager
import com.diastore.util.displayIntPickerBottomSheetDialog
import com.diastore.util.extensions.encryptUser
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class OnBoardingFragment : BaseFragment<OnBoardingBinding, SignUpViewModel>(R.layout.fragment_onboarding) {
    override val viewModel by sharedViewModel<SignUpViewModel>()
    private val sharedPreferencesManager by inject<SharedPreferencesManager>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.buttonConfirm.setOnClickListener {
            viewModel.signUpUser()
        }

        binding.ageField.setOnClickListener {
            displayIntPickerBottomSheetDialog(
                requireContext(),
                layoutInflater,
                binding.root as ViewGroup,
                1,
                100,
                viewModel.age.value,
                getString(R.string.profile_bottom_sheet_dialog_age_title),
                onConfirmClickListener = { age -> viewModel.age.value = age })
        }

        binding.weightField.setOnClickListener {
            displayIntPickerBottomSheetDialog(
                requireContext(),
                layoutInflater,
                binding.root as ViewGroup,
                20,
                200,
                viewModel.weight.value,
                getString(R.string.profile_bottom_sheet_dialog_weight_title),
                onConfirmClickListener = { weight -> viewModel.weight.value = weight })
        }

        binding.heightField.setOnClickListener {
            displayIntPickerBottomSheetDialog(
                requireContext(),
                layoutInflater,
                binding.root as ViewGroup,
                50,
                200,
                viewModel.height.value,
                getString(R.string.profile_bottom_sheet_dialog_height_title),
                onConfirmClickListener = { height -> viewModel.height.value = height })
        }

        binding.carbsToInsulinUnitField.setOnClickListener {
            displayIntPickerBottomSheetDialog(
                requireContext(),
                layoutInflater,
                binding.root as ViewGroup,
                1,
                50,
                viewModel.carbsToInsulinUnit.value,
                getString(R.string.profile_bottom_sheet_dialog_carbs_title),
                onConfirmClickListener = { carbsToInsulin -> viewModel.carbsToInsulinUnit.value = carbsToInsulin })
        }

        binding.bloodSugarInsulinUnitField.setOnClickListener {
            displayIntPickerBottomSheetDialog(
                requireContext(),
                layoutInflater,
                binding.root as ViewGroup,
                1,
                15,
                viewModel.bloodSugarInsulinUnit.value,
                getString(R.string.profile_bottom_sheet_dialog_blood_sugar_title),
                5,
                onConfirmClickListener = { bloodSugarInsulinUnit -> viewModel.bloodSugarInsulinUnit.value = bloodSugarInsulinUnit })
        }

        viewModel.signUpResponse.observe(viewLifecycleOwner, Observer {
            showAndEncrypt()
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), getString(R.string.error_message), Toast.LENGTH_SHORT).show()
        })
    }

    private fun showAndEncrypt() {
        DiaStoreBiometricPrompt(requireContext(), this, requireContext().mainExecutor) { _ ->
            viewModel.signUpResponse.value?.let {
                viewModel.saveEncryptedUser(it.encryptUser())
                sharedPreferencesManager.saveUser(
                    it.id.toString(),
                    it.firstName,
                    it.lastName
                )
                findNavController().navigate(OnBoardingFragmentDirections.actionAboutYouFragmentToMainNavigation())
            }
        }.authenticate()
    }
}