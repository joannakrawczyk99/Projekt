package com.example.projecting

data class Reaction<T>(
    var result: Result,
    val data: T? = null,
    val exception: Exception? = null,
    val errorMassage: String? = null
) {
    companion object {
        fun <T> onSuccess(data: T?): Reaction<T> {
            return Reaction(
                Result.SUCCESS,
                data
            )
        }

        fun <T> onError(exception: Exception? = null): Reaction<T> {
            return Reaction(
                Result.ERROR,
                exception = exception
            )
        }
    }
}


