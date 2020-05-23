package com.example.projecting.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.projecting.ClickOn
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

class UserFragment : Fragment(), OnMapReadyCallback, ClickOn.ClickOnAlbum{
    private lateinit var binding: UserFragmentBinding
    private lateinit var userData: User
    private lateinit var googleMap: GoogleMap
    private val userViewModel: UserViewModel by viewModel()
    val args: UserFragmentArgs by navArgs() //This take userId only from post

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = UserFragmentBinding.inflate(inflater, container, false)
        getUserData(args.userId)
        observeLiveData()

        binding.albumbtn.setOnClickListener(View.OnClickListener {
            clickAlbum(args.userId)
        })

        return binding.root
    }

    private fun observeLiveData() {
        userViewModel.userLiveData.observe(viewLifecycleOwner, Observer {
            onUserDataReceived(it)
        })
    }

    private fun onUserDataReceived(userData: User){
        this.userData = userData
        if (this::userData.isInitialized) {
            val user_geo =
                userData.address?.geo?.lat?.let { userData.address?.geo?.lng?.let { it1 ->
                    LatLng(it,
                        it1
                    )
                } }
            googleMap.addMarker(
                user_geo?.let {
                    MarkerOptions().position(it)
                        .title("user_location")
                }
            )
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user_geo, 3f))
            googleMap.uiSettings?.isMyLocationButtonEnabled = false
            googleMap.uiSettings?.isTiltGesturesEnabled = false
        }

        binding.user = userData
    }

    private fun getUserData(userId: Int){
        userViewModel.getUserData(userId)
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
    }

    override fun clickAlbum(userId: Int?) {
        val action = userId?.let {
            UserFragmentDirections.actionUserInfoToAlbumList(it)
        }

        if (action != null) {
            findNavController().navigate(action)
        }
    }

}

