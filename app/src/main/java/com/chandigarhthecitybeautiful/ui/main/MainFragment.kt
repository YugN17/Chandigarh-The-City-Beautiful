package com.chandigarhthecitybeautiful.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.chandigarhthecitybeautiful.MyApplication
import com.chandigarhthecitybeautiful.R
import com.chandigarhthecitybeautiful.adpaters.PlaceGridAdapter
import com.chandigarhthecitybeautiful.databinding.MainFragmentBinding
import com.chandigarhthecitybeautiful.model.Place

class MainFragment : Fragment(), PlaceGridAdapter.onClickListener {


    private lateinit var placeGridAdapter: PlaceGridAdapter
    private lateinit var placesListView: RecyclerView
    private lateinit var places: List<Place>
    private val viewModel by viewModels<MainViewModel>() {
        MainViewModel.PlacesViewModelFactory((requireContext().applicationContext as MyApplication).placesDataRepository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val mainFragmentBinding = MainFragmentBinding.inflate(inflater,container,false)
        placesListView = mainFragmentBinding.rvPlaceItems
        placesListView.setHasFixedSize(true)
        placesListView.isNestedScrollingEnabled = false
        return mainFragmentBinding.root
    }

    @ExperimentalStdlibApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.allPlaces.observe(viewLifecycleOwner, Observer {
            setupList(it)
        })
        viewModel.syncDataFromServer()
    }

    private fun setupList(places:List<Place>)
    {
        placeGridAdapter = PlaceGridAdapter(places, this)
        placesListView.adapter = placeGridAdapter
    }

    override fun onClick(place: Place) {
        val nextAction =  MainFragmentDirections.actionMainFragmentToPlaceDetails(place.id)
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            .navigate(nextAction)

    }
}