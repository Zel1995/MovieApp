package com.example.movieapp.ui.map

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.MainActivity
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentMapsBinding
import com.example.movieapp.location.LocationRepository
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MapsFragment : Fragment() {

    companion object {
        const val DEFAULT_LATITUDE: Double = -34.0
        const val DEFAULT_LONGITUDE: Double = 151.0
    }

    @Inject
    lateinit var factory: LocationViewModelFactory
    @Inject
    lateinit var locationRepository: LocationRepository
    private lateinit var viewBinding: FragmentMapsBinding
    private lateinit var viewModel: LocationViewModel
    private val mainScope = MainScope()
    private var longitude: Double? = null
    private var latitude: Double? = null
    private var mapFragment: SupportMapFragment? = null

    private val callback = OnMapReadyCallback { googleMap ->

        googleMap.clear()
        googleMap.uiSettings.isZoomControlsEnabled = true

        val place =
            LatLng(latitude ?: DEFAULT_LATITUDE, longitude ?: DEFAULT_LONGITUDE)
        googleMap.addMarker(MarkerOptions().position(place).title("Marker"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(place))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as? MainActivity)?.mainSubcomponent?.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        viewBinding = FragmentMapsBinding.bind(view)
        initLocationListener()
        initViewModel()
        collectViewModelData()
        initMenu()
        mapFragment?.getMapAsync(callback)
    }

    private fun initLocationListener() {
        mainScope.launch {
            locationRepository.getFlowLocation().collect {
                Toast.makeText(requireContext(),it.longitude.toString(),Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun initMenu() {
        viewBinding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.find_my_location_item -> {
                    viewModel.getLocation()
                    mapFragment?.getMapAsync(callback)
                    true
                }
                R.id.find_location_item -> {
                    if (!viewBinding.latitudeInput.text.isNullOrEmpty() || !viewBinding.longitudeInput.text.isNullOrEmpty()) {
                        latitude = viewBinding.latitudeInput.text.toString().toDouble()
                        longitude = viewBinding.longitudeInput.text.toString().toDouble()
                        mapFragment?.getMapAsync(callback)
                    }
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity(), factory).get(LocationViewModel::class.java)
    }

    private fun collectViewModelData() {
        mainScope.launch {
            viewModel.address.collect {
                Snackbar.make(viewBinding.root, it, Snackbar.LENGTH_SHORT).show()
            }
        }
        mainScope.launch {
            viewModel.lastLocation.collect {
                latitude = it.latitude
                longitude = it.longitude
            }
        }
    }
}
