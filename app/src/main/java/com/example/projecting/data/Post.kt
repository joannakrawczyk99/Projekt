package com.example.projecting.data

data class Post (
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
    var username: String,
    var countComments: Int
)

