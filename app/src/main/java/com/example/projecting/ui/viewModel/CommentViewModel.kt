package com.example.projecting.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projecting.data.Comment
import com.example.projecting.data.Post
import com.example.projecting.repo.CommentRepo
import com.example.projecting.repo.PostRepo
import com.example.projecting.Reaction
import com.example.projecting.Result
import kotlinx.coroutines.launch

class CommentViewModel(
    val commentRepo: CommentRepo,
    val postRepo: PostRepo) : ViewModel() {

    val commentsLiveData: MutableLiveData<List<Comment>> = MutableLiveData()
    val postLiveData: MutableLiveData<Post> = MutableLiveData()

    fun getComments(post: Post){
        viewModelScope.launch{
            val listOfComments = commentRepo.getComments()
            val reaction = Reaction(
                listOfComments.result, listOfComments.data?.filter{
                    it.postId == post.id
                })
            updateComments(reaction)
        }
    }

    fun getPost(postId: Int){
        viewModelScope.launch{
            val apiResult = postRepo.getPosts()
            val react = Reaction(
                apiResult.result, apiResult.data?.filter{
                    it.id == postId
                }?.first())
            updatePostLiveData(react)
        }
    }

    fun updateComments(reaction: Reaction<List<Comment>>){
        if (reaction.result == Result.SUCCESS){
            commentsLiveData.postValue(reaction.data)
        }
    }

    fun updatePostLiveData(reaction: Reaction<Post>){
        if (reaction.result == Result.SUCCESS){
            postLiveData.postValue(reaction.data)
        }
    }

}
