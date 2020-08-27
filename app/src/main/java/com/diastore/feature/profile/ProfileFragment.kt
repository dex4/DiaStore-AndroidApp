package com.diastore.feature.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.diastore.ProfileBinding
import com.diastore.R
import com.diastore.shared.DiaStoreBiometricPrompt
import com.diastore.util.BaseFragment
import com.diastore.util.SharedPreferencesManager
import com.diastore.util.displayIntPickerBottomSheetDialog
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext
import java.util.UUID

class ProfileFragment : BaseFragment<ProfileBinding, ProfileViewModel>(R.layout.fragment_profile) {
    override val viewModel by viewModel<ProfileViewModel>()
    private val sharedPreferencesManager by inject<SharedPreferencesManager>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.firstName.value = sharedPreferencesManager.getUserFirstName()
        viewModel.lastName.value = sharedPreferencesManager.getUserLastName()
        viewModel.clearPreviousAuthenticationData()
        authenticateForDecryption()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.buttonConfirm.setOnClickListener {
            authenticateForEncryption()
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

        binding.buttonLogOut.setOnClickListener {
            viewModel.clearUserData()
        }

        viewModel.deletedData.observe(viewLifecycleOwner, Observer {
            if (it != -1) {
                sharedPreferencesManager.clearUserData()
                restartActivity()
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), getString(R.string.error_message), Toast.LENGTH_SHORT).show()
        })
    }

    private fun restartActivity() {
        GlobalContext.stop()
        requireActivity().finish()
        requireActivity().startActivity(requireActivity().intent)
    }

    private fun authenticateForDecryption() {
        DiaStoreBiometricPrompt(requireContext(), this, requireContext().mainExecutor) { _ ->
            viewModel.getEncryptedUser()
        }.authenticate()
    }

    private fun authenticateForEncryption() {
        DiaStoreBiometricPrompt(requireContext(), this, requireContext().mainExecutor) { _ ->
            sharedPreferencesManager.getUserId()?.let { id ->
                viewModel.updateUser(UUID.fromString(id))
            }
        }.authenticate()
    }
}