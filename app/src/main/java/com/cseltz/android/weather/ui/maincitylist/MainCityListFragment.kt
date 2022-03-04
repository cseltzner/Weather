package com.cseltz.android.weather.ui.maincitylist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cseltz.android.weather.databinding.FragmentMainCityListBinding
import com.cseltz.android.weather.ui.uidataclasses.WeatherCity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*

@AndroidEntryPoint
class MainCityListFragment: Fragment(), MainCityListFragmentAdapter.OnClickListeners {

    private lateinit var binding: FragmentMainCityListBinding
    private val viewModel: MainCityListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainCityListBinding.inflate(inflater)
        val adapter = MainCityListFragmentAdapter(this)
        binding.mainRecyclerView.adapter = adapter
        binding.mainRecyclerView.setHasFixedSize(true)

        viewModel.cityList.observe(viewLifecycleOwner) { cityList ->
            viewModel.getWeatherCityList { list ->
                Log.d("MainCityListFragment", "Submitting list with ${list.size} items")
                adapter.apply {
                    submitList(null) // Needed because submitList needs a new reference to call for updates
                    submitList(list)
                }
            }
        }

        binding.mainFabAdd.setOnClickListener {
            viewModel.performEvent(MainCityListEvents.OnAddFabClicked)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is MainCityListUiEvents.Loading -> {
                        binding.mainProgressBar.isVisible = true
                    }

                    is MainCityListUiEvents.Failure -> {
                        binding.mainProgressBar.isVisible = false
                        Snackbar.make(binding.root, event.message, Snackbar.LENGTH_LONG).show()
                    }

                    is MainCityListUiEvents.Success -> {
                        binding.mainProgressBar.isVisible = false
                    }

                    is MainCityListUiEvents.NavigateToAddScreen -> {
                        val action = MainCityListFragmentDirections.actionMainCityListFragmentToAddCityFragment()
                        findNavController().navigate(action)
                    }

                }
            }
        }
        return binding.root
    }
}