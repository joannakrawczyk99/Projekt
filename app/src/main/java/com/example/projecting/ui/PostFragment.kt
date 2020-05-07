package com.example.projecting.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projecting.ClickOn
import com.example.projecting.ui.viewModel.PostAdapter
import com.example.projecting.ui.viewModel.PostViewModel
import com.example.projecting.data.Post
import com.example.projecting.databinding.PostFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostFragment() : Fragment(), ClickOn.ClickOnUser, ClickOn.ClickOnPost{

    private val postViewModel: PostViewModel by viewModel()
    private lateinit var binding: PostFragmentBinding
    private lateinit var postAdapter: PostAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = PostFragmentBinding.inflate(inflater, container, false)

        setupRecyclerView()
        observeLiveData()
        getPosts()

        return binding.root
    }

    private fun setupRecyclerView() {
        val rView: RecyclerView = binding.postsView
        val layoutManager: GridLayoutManager = GridLayoutManager(activity, GridLayoutManager.VERTICAL)
        rView.layoutManager = layoutManager

        postAdapter =
            PostAdapter(
                listOf(),
                this,
                this
            )
        rView.adapter = postAdapter
    }

    private fun observeLiveData() {
        postViewModel.postsLiveData.observe(viewLifecycleOwner, Observer { onPostsReceived(it) })
    }

    private fun getPosts() {
        postViewModel.getPosts()
    }

    private fun onPostsReceived(posts: List<Post>) {
        binding.post = posts.first()
        postAdapter.updatePosts(posts)
    }

    override fun clickUser(userId: Int?) {
        val action = userId?.let {
            PostFragmentDirections.actionPostListToUserInfo(it)
        }
        if (action != null) {
            findNavController().navigate(action)
        }
    }

    override fun clickPost(post: Post) {
        val action = post.id.let {
            PostFragmentDirections.actionPostListToCommentsList(it)
        }
        findNavController().navigate(action)
    }
}
