package com.example.projecting.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projecting.data.User
import com.example.projecting.repo.UserRepo
import kotlinx.coroutines.launch
import com.example.projecting.Reaction
import com.example.projecting.Result

class UserViewModel(val userRepo: UserRepo): ViewModel() {
    val userLiveData: MutableLiveData<User> = MutableLiveData()

    fun getUserData(userId: Int){
        viewModelScope.launch {
            val usersList = userRepo.getListOfUsers()
            val reaction =  Reaction(usersList.result, usersList.data?.get(userId - 1))

            updateUsers(reaction)
        }
    }

    private fun updateUsers(reaction: Reaction<User>){
        if(reaction.result == Result.SUCCESS){
            userLiveData.postValue(reaction.data)
        }
    }
}