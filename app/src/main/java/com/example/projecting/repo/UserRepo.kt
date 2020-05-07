package com.example.projecting.repo

import com.example.projecting.data.User
import com.example.projecting.network.JsonApiService
import com.example.projecting.Reaction
import com.example.projecting.Result
import java.lang.Exception


class UserRepo(val jsonApiService: JsonApiService) : ApiRequest() {
    suspend fun getNames(): Reaction<List<String>> {
        try{
            return getUsername(apiRequest(
                onSucc = {
                    jsonApiService.getUsers().await()
                }
            ))
        }
        catch(exception: Exception) {
            exception.printStackTrace()
            return Reaction(Result.ERROR, null)
        }
    }

    suspend fun getListOfUsers(): Reaction<List<User>> = apiRequest(
        onSucc = {
            jsonApiService.getUsers().await()
        }
    )
}

fun getUsername(reaction: Reaction<List<User>>): Reaction<List<String>> {
    val userList: List<String> = reaction.data!!.map{
        it.name
    }
    return Reaction(reaction.result, userList)
}