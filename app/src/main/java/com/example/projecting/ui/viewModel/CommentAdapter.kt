package com.example.projecting.ui.viewModel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projecting.data.Comment
import com.example.projecting.databinding.CommentViewBinding

class CommentAdapter(
    var comments : List<Comment>
) : RecyclerView.Adapter<CommentAdapter.CommentsViewHolder>() {

    inner class CommentsViewHolder(
         val binding: CommentViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun databind(comment: Comment){
            binding.comment = comment
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CommentViewBinding.inflate(layoutInflater)

        return CommentsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(
        holder: CommentsViewHolder, position: Int)
            = holder.databind(comments[position])

    fun updateComments(comments: List<Comment>){
        this.comments = comments
        notifyDataSetChanged()
    }
}