package com.cseltz.android.weather.ui.maincitylist

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cseltz.android.weather.NavGraphDirections
import com.cseltz.android.weather.R
import com.cseltz.android.weather.databinding.FragmentMainCityListBinding
import com.cseltz.android.weather.ui.uidataclasses.WeatherCity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MainCityListFragment: Fragment(), MainCityListFragmentAdapter.OnItemClickListeners {

    private lateinit var binding: FragmentMainCityListBinding
    private val viewModel: MainCityListViewModel by activityViewModels()
    private lateinit var adapter: MainCityListFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainCityListBinding.inflate(inflater)
        adapter = MainCityListFragmentAdapter(listOf(), this)
        binding.mainRecyclerView.adapter = adapter
        binding.mainRecyclerView.setHasFixedSize(true)

        binding.mainTimeLastUpdatedTextview.text = when(viewModel.lastUpdatedTime) {
            "" -> ""
            else -> "Last updated: ${viewModel.lastUpdatedTime}"
        }

        viewModel.cityList.observe(viewLifecycleOwner) {
            viewModel.getWeatherCityList { list ->
                Log.d("MainCityListFragment", "Submitting list with ${list.size} items")
                adapter.submitList(list)
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

                    is MainCityListUiEvents.UpdateRefreshTime -> {
                        binding.mainTimeLastUpdatedTextview.text = "Last updated: ${viewModel.getUpdatedTime()}"
                    }

                    is MainCityListUiEvents.NavigateToDeleteAllDialog -> {
                        val action = MainCityListFragmentDirections.actionMainCityListFragmentToDeleteAllDialog()
                        findNavController().navigate(action)
                    }

                    is MainCityListUiEvents.DeleteAllSuccess -> {
                        binding.mainTimeLastUpdatedTextview.text = ""
                    }

                }
            }
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_main_city_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_main_about -> {
                findNavController().navigate(NavGraphDirections.actionGlobalAboutFragment2())
                true
            }
            R.id.menu_main_delete_all -> {
                viewModel.performEvent(MainCityListEvents.OnDeleteAllClicked)
                true
            }
            R.id.menu_main_refresh -> {
                viewModel.performEvent(MainCityListEvents.OnRefreshClicked { list ->
                    adapter.submitList(list)
                })
                true
            }

            else -> { false }
        }
    }

    override fun onClick(weatherCity: WeatherCity) {
        val action = MainCityListFragmentDirections.actionMainCityListFragmentToSingleCityFragment3(weatherCity)
        findNavController().navigate(action)
    }

}