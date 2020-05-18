package com.diastore.feature.entrydetails

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.diastore.EntryDetailsBinding
import com.diastore.R
import com.diastore.util.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.threeten.bp.LocalDateTime

class EntryDetailsFragment : BaseFragment<EntryDetailsBinding, EntryDetailsViewModel>(R.layout.fragment_entry_details) {
    override val viewModel by sharedViewModel<EntryDetailsViewModel>()
    private val args by navArgs<EntryDetailsFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setSelectedEntry(args.entry)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.entryTimeField.setOnClickListener {
            val localDateTime = LocalDateTime.now()
            DatePickerDialog(requireContext()).apply {
                setOnDateSetListener { _, year, month, dayOfMonth ->
                    TimePickerDialog(
                        requireContext(),
                        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                            setEntryTime(year, month + 1, dayOfMonth, hourOfDay, minute)
                        },
                        localDateTime.hour,
                        localDateTime.minute,
                        true
                    ).show()
                }
                show()
            }
        }

        binding.buttonSave.setOnClickListener {
            viewModel.updateEntry()
            findNavController().navigateUp()
        }
    }

    private fun setEntryTime(year: Int, month: Int, day: Int, hour: Int, minute: Int) {
        viewModel.entryTime.value = LocalDateTime.of(year, month, day, hour, minute)
    }
}