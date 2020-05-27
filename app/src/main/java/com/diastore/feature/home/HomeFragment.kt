package com.diastore.feature.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.diastore.DiaStoreActivity
import com.diastore.DrawerHeaderBinding
import com.diastore.HomeBinding
import com.diastore.R
import com.diastore.feature.entrydetails.EntryDetailsViewModel
import com.diastore.util.BaseFragment
import com.diastore.util.SharedPreferencesManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeBinding, HomeViewModel>(R.layout.fragment_home) {
    override val viewModel by viewModel<HomeViewModel>()
    private val entryDetailsSharedViewModel by sharedViewModel<EntryDetailsViewModel>()
    private val adapter: EntryAdapter = EntryAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navView.setupWithNavController(findNavController())
        DrawerHeaderBinding.bind(binding.navView.getHeaderView(0)).apply {
            val sharedPreferencesManager = SharedPreferencesManager(activity as DiaStoreActivity)
            userName = getString(
                R.string.drawer_user_name_format,
                sharedPreferencesManager.getUserFirstName(),
                sharedPreferencesManager.getUserLastName()
            )
        }

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
            viewModel.getEntries()
        }

        entryDetailsSharedViewModel.updatedEntry.observe(viewLifecycleOwner, Observer {
            viewModel.handleSelectedEntryChange(it)
        })

        viewModel.entries.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.challenges -> findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToChallengesFragment())
                R.id.settings -> {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())
                }
                R.id.about -> findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAboutFragment())
                R.id.statistics -> Toast.makeText(context, it.title, Toast.LENGTH_SHORT).show()
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