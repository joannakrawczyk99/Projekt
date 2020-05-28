package com.example.projecting.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projecting.data.Post
import com.example.projecting.repo.CommentRepo
import com.example.projecting.repo.PostRepo
import com.example.projecting.repo.UserRepo
import com.example.projecting.Reaction
import com.example.projecting.Result
import kotlinx.coroutines.launch

class PostViewModel(val postRepo: PostRepo, val userRepo: UserRepo, val commentRepo: CommentRepo) : ViewModel(){
    val postsLiveData: MutableLiveData<List<Post>> = MutableLiveData()

    var postPagingLimit = 10
    var postPagingStart = 0



    fun getPosts() {
        viewModelScope.launch {
            val listOfPosts = postRepo.getPagedPosts(postPagingStart, postPagingLimit)
            val listOfUsers = userRepo.getNames()
            val quantityoOfComments = commentRepo.getCount(listOfPosts.data)

            listOfPosts.data?.map {
                it.username = listOfUsers.data!![it.userId-1]
            }

            listOfPosts.data?.map {
                it.countComments = quantityoOfComments.data!![it.userId-1]
            }
            updatePosts(listOfPosts)
        }
    }

    private fun updatePosts(reaction: Reaction<List<Post>>) {
        if (reaction.result == Result.SUCCESS) {
            postsLiveData.postValue(reaction.data)
        }
    }

}