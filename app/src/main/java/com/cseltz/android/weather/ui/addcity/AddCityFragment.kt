package com.cseltz.android.weather.ui.addcity

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cseltz.android.weather.R
import com.cseltz.android.weather.databinding.FragmentAddCityBinding
import com.cseltz.android.weather.util.StateIdToStateCodeConverter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.*

@AndroidEntryPoint
class AddCityFragment: Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentAddCityBinding
    private val viewModel: AddCityViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCityBinding.inflate(inflater)

        // Set spinner adapter for string-array resource
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.states,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.stateSpinner.adapter = adapter
        }
        binding.stateSpinner.onItemSelectedListener = this
        binding.stateSpinner.setSelection(viewModel.currentStateSpinnerPosition, false)

        binding.cityEdittext.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Nothing
            }
            override fun onTextChanged(newString: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.cityEditTextValue = newString.toString()
            }
            override fun afterTextChanged(p0: Editable?) {
                // Nothing
            }
        })
        binding.cityEdittext.setText(viewModel.cityEditTextValue)
        // Performs action on done button
        // Refactor this into its own functions later
        binding.cityEdittext.setOnEditorActionListener { textView, actionId, keyEvent ->
          if (actionId == EditorInfo.IME_ACTION_DONE) {
              if (viewModel.validateCity(viewModel.cityEditTextValue, viewModel.currentStateSpinnerPosition)) {
                  val state = StateIdToStateCodeConverter.convertStateSpinnerPositionToStateCode(viewModel.currentStateSpinnerPosition)
                  viewModel.performEvent(AddCityEvents.OnCompletedFabClick(viewModel.cityEditTextValue, state))
              } else {
                  Snackbar.make(binding.root, "Please enter a city", Snackbar.LENGTH_SHORT).show()
              }

          }
            false
        }

        binding.doneFab.setOnClickListener {
            if (viewModel.validateCity(viewModel.cityEditTextValue, viewModel.currentStateSpinnerPosition)) {
                val state = StateIdToStateCodeConverter.convertStateSpinnerPositionToStateCode(viewModel.currentStateSpinnerPosition)
                viewModel.performEvent(AddCityEvents.OnCompletedFabClick(viewModel.cityEditTextValue, state))
            } else {
                Snackbar.make(binding.root, "Please enter a city", Snackbar.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is AddCityUiEvents.Loading -> {
                        binding.addCityProgressBar.isVisible = true
                    }

                    is AddCityUiEvents.Failure -> {
                        binding.addCityProgressBar.isVisible = false
                        Snackbar.make(binding.root, event.message, Snackbar.LENGTH_LONG).show()
                    }

                    is AddCityUiEvents.Success -> {
                        binding.addCityProgressBar.isVisible = false
                        findNavController().popBackStack()
                    }
                }
            }

        }

        return binding.root
    }

    // On spinner item selected
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        viewModel.currentStateSpinnerPosition = pos
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        // nothing
    }
}