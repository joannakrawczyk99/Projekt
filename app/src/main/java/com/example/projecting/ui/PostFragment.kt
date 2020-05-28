package com.example.projecting.ui

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projecting.ClickOn
import com.example.projecting.data.Post
import com.example.projecting.databinding.PostFragmentBinding
import com.example.projecting.ui.viewModel.PostAdapter
import com.example.projecting.ui.viewModel.PostViewModel
import kotlinx.android.synthetic.main.post_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

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
        val layoutManager = GridLayoutManager(activity, GridLayoutManager.VERTICAL)
        rView.layoutManager = layoutManager


        postAdapter =
            PostAdapter(
                listOf(),
                this,
                this
            )
        rView.adapter = postAdapter

        rView.addOnScrollListener(this@PostFragment.scrollListener)

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

    var isScrolling = false
    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            ProgressBar.visibility = View.VISIBLE

            val layoutManager = recyclerView.layoutManager as GridLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThenVisible = totalItemCount >= 10
            val shouldPaginate =  isAtLastItem && isNotAtBeginning && isTotalMoreThenVisible && isScrolling
            if(shouldPaginate){


                postViewModel.postPagingLimit += 10
                Handler().postDelayed(
                    {
                        postViewModel.getPosts()
                        ProgressBar.visibility = View.GONE},5000
                )





                isScrolling = false

            }

        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true

            }
        }
    }


}
