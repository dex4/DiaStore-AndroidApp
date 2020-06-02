package com.diastore.feature.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.diastore.DiaStoreActivity
import com.diastore.ProfileBinding
import com.diastore.R
import com.diastore.util.BaseFragment
import com.diastore.util.EncryptionUtils
import com.diastore.util.SharedPreferencesManager
import com.diastore.util.displayIntPickerBottomSheetDialog
import com.diastore.util.extensions.encryptUser
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.UUID

class ProfileFragment : BaseFragment<ProfileBinding, ProfileViewModel>(R.layout.fragment_profile) {
    override val viewModel by viewModel<ProfileViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val sharedPreferencesManager = SharedPreferencesManager(activity as DiaStoreActivity)
        viewModel.firstName.value = sharedPreferencesManager.getUserFirstName()
        viewModel.lastName.value = sharedPreferencesManager.getUserLastName()
        viewModel.clearPreviousAuthenticationData()
        viewModel.getEncryptedUser()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.buttonConfirm.setOnClickListener {
            showAndEncrypt(false)
        }

        viewModel.encryptedUser.observe(viewLifecycleOwner, Observer {
            showAndEncrypt(true)
        })

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
    }

    private fun showAndEncrypt(isForDecryption: Boolean) {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("DiaStoreAuth")
            .setSubtitle("LogIn using your biometric cred.")
            .setNegativeButtonText("Use password.")
            .build()

        if (isForDecryption) {
            BiometricPrompt(this, requireContext().mainExecutor, decryptAuthenticationCallback)
                .authenticate(promptInfo)
        } else {
            BiometricPrompt(this, requireContext().mainExecutor, encryptAuthenticationCallback)
                .authenticate(promptInfo)
        }
    }

    fun displayFingerprintAuthenticationError(error: String) {
        Toast.makeText(requireContext(), "Authentication error: ", Toast.LENGTH_SHORT).show()
    }

    fun displayFingerprintAuthenticationFailed() {
        Toast.makeText(context, "Fingerprint not recognized", Toast.LENGTH_SHORT).show()
    }

    private val decryptAuthenticationCallback = object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationError(
            errorCode: Int,
            errString: CharSequence
        ) {
            super.onAuthenticationError(errorCode, errString)
            displayFingerprintAuthenticationError(errString.toString())
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            viewModel.encryptedUser.value?.let {
                viewModel.email.value = EncryptionUtils.decrypt(it.email)
                viewModel.age.value = EncryptionUtils.decrypt(it.age).toInt()
                viewModel.bloodSugarInsulinUnit.value = EncryptionUtils.decrypt(it.bloodGlucoseToInsulinRatio).toInt()
                viewModel.carbsToInsulinUnit.value = EncryptionUtils.decrypt(it.carbsToInsulinRatio).toInt()
                viewModel.weight.value = EncryptionUtils.decrypt(it.weight).toInt()
                viewModel.height.value = EncryptionUtils.decrypt(it.height).toInt()
            }
        }

        override fun onAuthenticationFailed() {
            displayFingerprintAuthenticationFailed()
        }
    }

    private val encryptAuthenticationCallback = object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationError(
            errorCode: Int,
            errString: CharSequence
        ) {
            super.onAuthenticationError(errorCode, errString)
            displayFingerprintAuthenticationError(errString.toString())
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            SharedPreferencesManager(activity as DiaStoreActivity).getUserId()?.let { id ->
                val user = viewModel.getUser(UUID.fromString(id))
                user?.encryptUser()?.let {
                    viewModel.saveEncryptedUser(it)
                    findNavController().navigateUp()
                }
            }
        }

        override fun onAuthenticationFailed() {
            displayFingerprintAuthenticationFailed()
        }
    }
}