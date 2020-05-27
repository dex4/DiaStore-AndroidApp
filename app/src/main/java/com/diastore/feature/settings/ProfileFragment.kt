package com.diastore.feature.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.diastore.DiaStoreActivity
import com.diastore.ProfileBinding
import com.diastore.R
import com.diastore.util.BaseFragment
import com.diastore.util.SharedPreferencesManager
import com.diastore.util.displayIntPickerBottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<ProfileBinding, ProfileViewModel>(R.layout.fragment_profile) {
    override val viewModel by viewModel<ProfileViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.getUser()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.buttonConfirm.setOnClickListener {
            SharedPreferencesManager(activity as DiaStoreActivity).getUserId()?.let { id ->
                viewModel.updateUser(id)
                findNavController().navigateUp()
            }
        }

//        binding.buttonLogOut.root.setOnClickListener {
//            SharedPreferencesManager(requireActivity() as DiaStoreActivity).clearUserData()
//            viewModel.clearUserData()
//            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToWelcomeFragment())
//        }

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
}