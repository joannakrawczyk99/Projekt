package com.example.projecting.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projecting.data.Album
import com.example.projecting.data.Photo
import com.example.projecting.databinding.PhotoFragmentBinding
import com.example.projecting.ui.viewModel.PhotoAdapter
import com.example.projecting.ui.viewModel.PhotoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoFragment : Fragment() {

    private val viewModel: PhotoViewModel by viewModel()
    private lateinit var binding: PhotoFragmentBinding
    private lateinit var photosAdapter: PhotoAdapter
    private lateinit var album: Album

    val args: PhotoFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = PhotoFragmentBinding.inflate(inflater, container, false)

        formRecyclerView()
        observeLiveData()
        getAlbum(args.albumId)
        return binding.root
    }


    private fun formRecyclerView() {
        val rView: RecyclerView = binding.photoRview
        val layoutManager = GridLayoutManager(activity, GridLayoutManager.VERTICAL)
        rView.layoutManager = layoutManager

        photosAdapter = PhotoAdapter(listOf())
        rView.adapter = photosAdapter
    }

    private fun observeLiveData() {
        viewModel.photosLiveData.observe(viewLifecycleOwner, Observer {
            onPhotosReceived(it)
        })
        viewModel.albumLiveData.observe(viewLifecycleOwner, Observer {
            onAlbumReceived(it)
        })
    }


    private fun onPhotosReceived(photos: List<Photo>) {
        photosAdapter.updatePhoto(photos)
    }

    private fun onAlbumReceived(album: Album){
        this.album = album
        binding.album = album
        getPhotos(album)
    }
    private fun getPhotos(album: Album) {
        viewModel.getPhotos(album)
    }

    private fun getAlbum(albumId: Int){
        viewModel.getAlbum(albumId)
    }


}
