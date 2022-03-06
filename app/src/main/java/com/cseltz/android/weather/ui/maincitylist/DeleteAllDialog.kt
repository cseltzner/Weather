package com.cseltz.android.weather.ui.maincitylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cseltz.android.weather.databinding.FragmentDeleteAllDialogBinding

class DeleteAllDialog: DialogFragment() {

    private lateinit var binding: FragmentDeleteAllDialogBinding
    // Uses same viewModel as fragment
    private val viewModel: MainCityListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeleteAllDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.deleteAllConfirm.setOnClickListener {
            viewModel.performEvent(MainCityListEvents.OnDeleteAllConfirmed)
            findNavController().popBackStack()
        }

        binding.deleteAllCancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}