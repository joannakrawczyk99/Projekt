package com.example.projecting.network

import com.example.projecting.data.*
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JsonApiService {

    @GET("/posts")
    fun getPost(): Deferred<List<Post>>

    @GET("/posts")
    fun getPageOfPostsAsync(@Query("_start") postStart: Int, @Query("_limit") postLimit : Int) : Deferred<List<Post>>

    @GET("/users")
    fun getUsers(): Deferred<List<User>>

    @GET("/comments")
    fun getComment(): Deferred<List<Comment>>

}

