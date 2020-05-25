package com.example.projecting.ui.viewModel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projecting.data.Post
import com.example.projecting.databinding.PostViewBinding
import kotlinx.android.synthetic.main.post_view.view.*
import com.example.projecting.ClickOn.*
import com.example.projecting.ui.PostFragment

class PostAdapter(
    var posts: List<Post>,
    var clickOnPost: ClickOnPost,
    var clickOnUser: PostFragment
) : RecyclerView.Adapter<PostAdapter.PostsViewHolder>(){

    inner class PostsViewHolder(
        val binding: PostViewBinding,
        val clickOnUser: PostFragment,
        val clickOnPost: ClickOnPost
    ) : RecyclerView.ViewHolder(binding.root) {

        fun databind(post: Post){
            binding.post = post
        }

        init{
            itemView.username_btn.setOnClickListener {
                this.clickOnUser.clickUser(posts[adapterPosition].userId)
            }
            itemView.comment_btn.setOnClickListener{
                this.clickOnPost.clickPost(posts[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PostViewBinding.inflate(layoutInflater)

        return PostsViewHolder(binding, clickOnUser, clickOnPost)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int)
            = holder.databind(posts[position])

    override fun getItemCount(): Int {
        return posts.size
    }

    fun updatePosts(posts: List<Post>){
        this.posts = posts
        notifyDataSetChanged()
    }
}