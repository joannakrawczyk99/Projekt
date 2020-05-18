package com.example.projecting.repo

import com.example.projecting.data.Comment
import com.example.projecting.data.Post
import com.example.projecting.network.JsonApiService
import com.example.projecting.Reaction


class CommentRepo(val jsonApiService: JsonApiService): ApiRequest() {
    suspend fun getCount(listOfPosts: List<Post>?): Reaction<List<Int>> {
            return countComments(listOfPosts, apiRequest(
                onSucc =  {
                    jsonApiService.getComment().await()
                }
            ))
    }
    suspend fun getComments(): Reaction<List<Comment>> = apiRequest (
        onSucc = {
            jsonApiService.getComment().await()
        }
    )
}

fun countComments(postList: List<Post>?, reaction: Reaction<List<Comment>>): Reaction<List<Int>> {
    val list: MutableList<Int> = mutableListOf()

    postList?.forEach { el ->
        run {
            list.add(reaction.data!!.count {
                it.postId == el.id
            })
        }
    }

    return Reaction(reaction.result, list)
}