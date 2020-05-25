package com.example.projecting.repo

import com.example.projecting.Reaction
import com.example.projecting.data.Photo
import com.example.projecting.network.JsonApiService

class PhotoRepo(val jsonApiService: JsonApiService): ApiRequest() {
    suspend fun getPhotos(): Reaction<List<Photo>> = apiRequest(
        onSucc = {
            jsonApiService.getPhoto().await()
        }
    )
}