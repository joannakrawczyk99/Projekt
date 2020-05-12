package com.example.projecting.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.projecting.ui.viewModel.UserViewModel
import com.example.projecting.data.User
import com.example.projecting.databinding.UserFragmentBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.user_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserFragment : Fragment(), OnMapReadyCallback{
    private val userViewModel: UserViewModel by viewModel()
    private lateinit var binding: UserFragmentBinding
    private lateinit var userData: User
    private lateinit var googleMap: GoogleMap
    val args: UserFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = UserFragmentBinding.inflate(inflater, container, false)

        observeLiveData()
        userData = getUserData(args.userId)

        return binding.root
    }

    private fun observeLiveData() {
        userViewModel.userLiveData.observe(viewLifecycleOwner, Observer { onUserDataReceived(it) })
    }

    //tutaj gdzieś ten user się nie inicjalizuje bo w val args pobierasz userID i dopiero po tym user ID jakby ściągasz "całego usera" i mapa inicjalizuje się zanim ci userzy się "ściągną"
    //tak to mniej więcej rozumiem. Generalnie trzeba by było najlepiej pobrać całegu usera od razu wcześniej, można podobnie do Lukasza, podeślę ci repo ale z tym też wiążą się potem komplikacje
    private fun onUserDataReceived(userData: User){
        this.userData = userData
        binding.user = userData
    }

    private fun getUserData(userId: Int): User {
       return  userViewModel.getUserData(userId)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let{
            googleMap = it
        }
        val userGeo = LatLng(userData.address.geo.lat.toDouble(), userData.address.geo.lng.toDouble())
        Log.d("latitude-onMapReady","${userData.address.geo.lat.toDouble()}" )
        Log.d("longitude-onMapReady","${userData.address.geo.lng.toDouble()}" )
        map?.addMarker(
            MarkerOptions().position(userGeo)
            .title("user_location"))
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(userGeo, 3f))
        map?.uiSettings?.isMyLocationButtonEnabled = false
        map?.uiSettings?.isTiltGesturesEnabled = false
    }

}
