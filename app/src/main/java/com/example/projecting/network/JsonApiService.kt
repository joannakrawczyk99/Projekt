package com.example.projecting.network

import com.example.projecting.data.*
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface JsonApiService {

    @GET("/posts")
    fun getPost(): Deferred<List<Post>>

    @GET("/users")
    fun getUsers(): Deferred<List<User>>

    @GET("/comments")
    fun getComment(): Deferred<List<Comment>>

    @GET("/albums")
    fun getAlbum(): Deferred<List<Album>>

    @GET("/users/{userId}/albums")
    fun getAlbumData(@Path("albumId") id: Int): Deferred<Album>

    @GET("/photos")
    fun getPhoto(): Deferred<List<Photo>>

    @GET("/albums/{albumId}/photos")
    fun getPhotoData(@Path("albumId") id: Int): Deferred<Photo>
}

