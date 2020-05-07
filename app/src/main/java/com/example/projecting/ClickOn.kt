package com.example.projecting

import com.example.projecting.data.Post

class ClickOn {
    interface ClickOnUser{
        fun clickUser(userId: Int?)
    }

    interface ClickOnPost{
        fun clickPost(post: Post)
    }
}