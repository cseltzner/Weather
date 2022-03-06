package com.cseltz.android.weather.ui.maincitylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cseltz.android.weather.databinding.FragmentDeleteAllDialogBinding
import com.cseltz.android.weather.databinding.FragmentDeleteCityDialogBinding

class DeleteCityDialog : DialogFragment() {
    private lateinit var binding: FragmentDeleteCityDialogBinding
    // Uses same viewModel as fragment
    private val viewModel: MainCityListViewModel by activityViewModels()
    private val args: DeleteCityDialogArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeleteCityDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.deleteCityConfirm.setOnClickListener {
            viewModel.performEvent(MainCityListEvents.OnDeleteCityClicked(args.weatherCity))
            findNavController().popBackStack()
        }

        binding.deleteCityCancel.setOnClickListener {
            // Just checking... TODO findNavController().popBackStack()
        }
    }

}