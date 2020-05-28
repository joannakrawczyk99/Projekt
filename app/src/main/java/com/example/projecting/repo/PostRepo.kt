package com.example.projecting.repo

import com.example.projecting.data.Post
import com.example.projecting.network.JsonApiService
import com.example.projecting.Reaction

class PostRepo(val jsonApiService: JsonApiService): ApiRequest() {
    suspend fun getPosts(): Reaction<List<Post>> = apiRequest (
        onSucc = {
            jsonApiService.getPost().await()
        }
    )

    suspend fun getPagedPosts(pageStart: Int, pageLimit: Int): Reaction<List<Post>> = apiRequest (
        onSucc = {
            jsonApiService.getPageOfPostsAsync(pageStart, pageLimit).await()
        }
    )
}