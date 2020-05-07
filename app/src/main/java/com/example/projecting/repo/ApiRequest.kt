package com.example.projecting.repo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.projecting.Reaction

open class ApiRequest() {
    suspend fun<T: Any> apiRequest(onSucc: suspend () -> T): Reaction<T> {
        return try{
            val react = withContext(Dispatchers.IO) {
                onSucc.invoke()
            }
            Reaction.onSuccess(react)
        }
        catch(exception: Exception){
            exception.printStackTrace()
            Reaction.onError(exception)
        }
    }
}

