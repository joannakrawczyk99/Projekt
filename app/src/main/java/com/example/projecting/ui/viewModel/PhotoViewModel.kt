package com.example.projecting.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projecting.Reaction
import com.example.projecting.Result
import com.example.projecting.data.Album
import com.example.projecting.data.Photo
import com.example.projecting.repo.AlbumRepo
import com.example.projecting.repo.PhotoRepo
import kotlinx.coroutines.launch

class PhotoViewModel(
    private val photoRepo: PhotoRepo,
    private val albumRepo: AlbumRepo
) : ViewModel(){

    val photosLiveData: MutableLiveData<List<Photo>> = MutableLiveData()
    val albumLiveData: MutableLiveData<Album> = MutableLiveData()

    fun getPhotos(album: Album){
        viewModelScope.launch {
            val apiResult = photoRepo.getPhotos()
            val react = Reaction(apiResult.result,apiResult.data?.filter {
                it.albumId == album.id
            })
            updatePhoto(react)
        }
    }

    fun getAlbum(albumId: Int){
        viewModelScope.launch {
            val apiResult = albumRepo.getAlbums()
            val react = Reaction(apiResult.result, apiResult.data?.filter {
                it.id == albumId
            }?.first())
            updateAlbum(react)
        }
    }


    fun updatePhoto(reaction: Reaction<List<Photo>>){
        if(reaction.result == Result.SUCCESS){
            photosLiveData.postValue(reaction.data)
        }
    }


    fun updateAlbum(reaction: Reaction<Album>){
        if(reaction.result == Result.SUCCESS){
            albumLiveData.postValue(reaction.data)
        }
    }




}