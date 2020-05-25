package com.example.projecting.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projecting.ClickOn
import com.example.projecting.data.Album
import com.example.projecting.data.User
import com.example.projecting.databinding.AlbumFragmentBinding
import com.example.projecting.ui.viewModel.AlbumAdapter
import com.example.projecting.ui.viewModel.AlbumViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumFragment : Fragment(), ClickOn.ClickOnPhoto{

    private val viewModel : AlbumViewModel by viewModel()
    private lateinit var binding: AlbumFragmentBinding
    private lateinit var albumAdapter: AlbumAdapter
    private lateinit var user: User

    val args: AlbumFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = AlbumFragmentBinding.inflate(inflater, container, false)

        formRecyclerView()
        observeLiveData()
        getUser(args.userId)

        return binding.root
    }

    private fun formRecyclerView() {
        val rView: RecyclerView = binding.albumRview
        val layoutManager = GridLayoutManager(activity, GridLayoutManager.VERTICAL)
        rView.layoutManager = layoutManager

        albumAdapter = AlbumAdapter(listOf(),this)
        rView.adapter = albumAdapter
    }

    private fun observeLiveData() {
        viewModel.albumLiveData.observe(viewLifecycleOwner, Observer {
            onAlbumReceived(it)
        })
        viewModel.userLiveData.observe(viewLifecycleOwner, Observer {
            onUserReceived(it)
        })
    }


    private fun onAlbumReceived(albums: List<Album>) {
        albumAdapter.updateAlbums(albums)
    }


    private fun onUserReceived(user: User) {
        this.user = user
        binding.user = user
        getAlbum(user)
    }

    private fun getAlbum(user: User){
        viewModel.getAlbums(user)
    }

    private fun getUser(userId: Int) {
        viewModel.getUser(userId)
    }

    override fun clickPhoto(albumId: Int?) {
        val action = albumId?.let{
            AlbumFragmentDirections.actionAlbumsListToPhotoList(it)
        }
        if(action != null)
            findNavController().navigate(action)
    }


}