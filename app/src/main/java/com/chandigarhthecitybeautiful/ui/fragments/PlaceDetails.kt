package com.chandigarhthecitybeautiful.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.chandigarhthecitybeautiful.MyApplication
import com.chandigarhthecitybeautiful.databinding.PlaceDetailsFragmentBinding
import com.chandigarhthecitybeautiful.viewmodels.PlaceDetailsViewModel

class PlaceDetails : Fragment() {

    private val viewModel by viewModels<PlaceDetailsViewModel>() {
        PlaceDetailsViewModel.PlaceDetailsViewModelFactory(
            (requireContext().applicationContext as MyApplication).placesDataRepository
        )
    }

    private lateinit var placeDetailsBinding: PlaceDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        placeDetailsBinding = PlaceDetailsFragmentBinding.inflate(inflater,container,false)
        return placeDetailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.place.observe(viewLifecycleOwner, Observer {
            placeDetailsBinding.place = it
        })
        arguments?.let {
            val safeArgs =
                PlaceDetailsArgs.fromBundle(it)
            viewModel.getPlaceData(safeArgs.placeId)
        }
    }
}