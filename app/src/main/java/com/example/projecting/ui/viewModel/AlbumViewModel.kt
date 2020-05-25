package com.example.projecting.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projecting.Reaction
import com.example.projecting.Result
import com.example.projecting.data.Album
import com.example.projecting.data.User
import com.example.projecting.repo.AlbumRepo
import com.example.projecting.repo.UserRepo
import kotlinx.coroutines.launch

class AlbumViewModel(
     val albumRepo: AlbumRepo,
     val userRepo: UserRepo
) : ViewModel() {

    val albumLiveData: MutableLiveData<List<Album>> = MutableLiveData()
    val userLiveData: MutableLiveData<User> = MutableLiveData()

    fun getAlbums(user: User) {
        viewModelScope.launch {
            val apiResult = albumRepo.getAlbums()
            val react =
                Reaction(apiResult.result, apiResult.data?.filter {
                    it.userId == user.id
                })
            updateAlbum(react)
        }
    }

    fun getUser(userId: Int) {

        viewModelScope.launch {
            val apiResult = userRepo.getListOfUsers()
            val react =
                Reaction(apiResult.result, apiResult.data?.filter {
                    it.id == userId
                }?.first())
            updateUser(react)
        }
    }

    fun updateUser(reaction: Reaction<User>) {
        if (reaction.result == Result.SUCCESS) {
            userLiveData.postValue(reaction.data)
        }
    }

    private fun updateAlbum(reaction: Reaction<List<Album>>) {
        if (reaction.result == Result.SUCCESS) {
            albumLiveData.postValue(reaction.data)
        }
    }
}