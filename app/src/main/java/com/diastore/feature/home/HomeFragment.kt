package com.diastore.feature.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.diastore.DrawerHeaderBinding
import com.diastore.HomeBinding
import com.diastore.R
import com.diastore.feature.entrydetails.EntryDetailsViewModel
import com.diastore.util.BaseFragment
import com.diastore.util.SharedPreferencesManager
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.UUID

class HomeFragment : BaseFragment<HomeBinding, HomeViewModel>(R.layout.fragment_home) {
    override val viewModel by viewModel<HomeViewModel>()
    private val entryDetailsSharedViewModel by sharedViewModel<EntryDetailsViewModel>()
    private val sharedPreferencesManager by inject<SharedPreferencesManager>()
    private val adapter: EntryAdapter = EntryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferencesManager.getUserId()?.let {
            viewModel.getEntries(UUID.fromString(it))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navView.setupWithNavController(findNavController())
        DrawerHeaderBinding.bind(binding.navView.getHeaderView(0)).apply {
            userName = getString(
                R.string.drawer_user_name_format,
                sharedPreferencesManager.getUserFirstName(),
                sharedPreferencesManager.getUserLastName()
            )
        }

        binding.entriesSwipeRefreshLayout.setColorSchemeColors(
            resources.getColor(R.color.colorPrimaryLight, null),
            resources.getColor(R.color.colorPrimary, null),
            resources.getColor(R.color.colorPrimaryDark, null)
        )

        binding.entriesRecycler.adapter = adapter.apply {
            setOnEntryClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToEntryDetailsFragment(it))
            }
            setOnDeleteEntryClickListener {
                viewModel.deleteEntry(it)
            }
        }

        binding.entriesRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    binding.buttonAdd.hide()
                } else {
                    binding.buttonAdd.show()
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        binding.entriesSwipeRefreshLayout.setOnRefreshListener {
            sharedPreferencesManager.getUserId()?.let {
                viewModel.getEntries(UUID.fromString(it))
            }
        }

        entryDetailsSharedViewModel.updatedEntry.observe(viewLifecycleOwner, Observer { entry ->
            sharedPreferencesManager.getUserId()?.let { userId ->
                viewModel.handleSelectedEntryChange(UUID.fromString(userId), entry)
            }
        })

        viewModel.entries.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), getString(R.string.error_message), Toast.LENGTH_SHORT).show()
        })

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.settings -> {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())
                }
                R.id.about -> findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAboutFragment())
            }
            true
        }

        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.buttonAdd.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToEntryDetailsFragment())
        }
    }
}