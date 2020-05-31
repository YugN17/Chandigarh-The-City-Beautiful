package com.chandigarhthecitybeautiful

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.chandigarhthecitybeautiful.databinding.PlaceDetailsFragmentBinding

class PlaceDetails : Fragment() {

    private val viewModel by viewModels<PlaceDetailsViewModel>() {
        PlaceDetailsViewModel.PlaceDetailsViewModelFactory((requireContext().applicationContext as MyApplication).placesDataRepository)
    }

    lateinit var placeDetailsBinding : PlaceDetailsFragmentBinding

    companion object {
        fun newInstance() = PlaceDetails()
    }

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
            requireActivity().actionBar!!.title = it.name
        })
        arguments?.let{
            val safeArgs =  PlaceDetailsArgs.fromBundle(it)
            viewModel.getPlaceData(safeArgs.placeId)
        }
    }
}