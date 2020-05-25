package com.example.projecting.repo

import com.example.projecting.Reaction
import com.example.projecting.data.Album
import com.example.projecting.network.JsonApiService

class AlbumRepo(val jsonApiService: JsonApiService): ApiRequest() {
    suspend fun getAlbums(): Reaction<List<Album>> = apiRequest(
        onSucc = {
            jsonApiService.getAlbum().await()
        }
    )
}