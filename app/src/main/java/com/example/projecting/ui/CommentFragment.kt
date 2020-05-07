package com.example.projecting.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projecting.ui.viewModel.CommentAdapter
import com.example.projecting.ui.viewModel.CommentViewModel
import com.example.projecting.data.Comment
import com.example.projecting.data.Post
import com.example.projecting.databinding.CommentFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class CommentFragment : Fragment() {
    private val commentViewModel: CommentViewModel by viewModel()

    private lateinit var binding: CommentFragmentBinding

    private lateinit var commentAdapter: CommentAdapter

    private lateinit var post: Post

    val args: CommentFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = CommentFragmentBinding.inflate(inflater, container, false)

        setupRecyclerView()
        observeLiveData()
        getPost(args.postId)

        return binding.root
    }

    private fun setupRecyclerView() {
        val rView: RecyclerView = binding.commentsView
        val layoutManager = GridLayoutManager(activity, GridLayoutManager.VERTICAL)
        rView.layoutManager = layoutManager

        commentAdapter =
            CommentAdapter(listOf())
        rView.adapter = commentAdapter
    }

    private fun observeLiveData() {
        commentViewModel.commentsLiveData.observe(viewLifecycleOwner, Observer { onCommentsReceived(it) })
        commentViewModel.postLiveData.observe(viewLifecycleOwner, Observer { onPostReceived(it) })
    }

    private fun onCommentsReceived(comments: List<Comment>) {
        commentAdapter.updateComments(comments)
    }
    private fun onPostReceived(post: Post){
        this.post = post
        binding.post = post
        getComments(post)
    }
    private fun getComments(post: Post) {
        commentViewModel.getComments(post)
    }
    private fun getPost(postId: Int){
        commentViewModel.getPost(postId)
    }
}
