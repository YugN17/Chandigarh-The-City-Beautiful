package com.chandigarhthecitybeautiful.ui.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.chandigarhthecitybeautiful.MyApplication
import com.chandigarhthecitybeautiful.R
import com.chandigarhthecitybeautiful.adpaters.PlaceGridAdapter
import com.chandigarhthecitybeautiful.databinding.MainFragmentBinding
import com.chandigarhthecitybeautiful.model.Place
import com.chandigarhthecitybeautiful.viewmodels.MainViewModel

class MainFragment : Fragment(), PlaceGridAdapter.onClickListener {


    private lateinit var placesListView: RecyclerView
    private val viewModel by viewModels<MainViewModel>() {
        MainViewModel.PlacesViewModelFactory(
            (requireContext().applicationContext as MyApplication)
                .placesDataRepository
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val mainFragmentBinding = MainFragmentBinding.inflate(inflater,container,false)
        placesListView = mainFragmentBinding.rvPlaceItems
        placesListView.setHasFixedSize(true)
        return mainFragmentBinding.root
    }

    @ExperimentalStdlibApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.allPlaces.observe(viewLifecycleOwner, Observer {
            setupList(it)
        })
        viewModel.msg.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty())
                showMsg(it)
        })
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(networkRequest, object :
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                viewModel.syncDataFromServer()
            }
        })
    }

    private fun setupList(places: List<Place>) {
        if (places.isNotEmpty()) {
            placesListView.adapter = PlaceGridAdapter(places, this)
        } else {
            showMsg("No data Available. Please connect your device to internet")
        }
    }

    private fun showMsg(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
        viewModel.setMessage("")
    }

    override fun onClick(place: Place) {
        val nextAction =
            MainFragmentDirections.actionMainFragmentToPlaceDetails(place.id, place.name)
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            .navigate(nextAction)

    }
}