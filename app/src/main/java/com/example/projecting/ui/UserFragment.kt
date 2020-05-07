package com.example.projecting.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.projecting.ui.viewModel.UserViewModel
import com.example.projecting.data.User
import com.example.projecting.databinding.UserFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserFragment : Fragment() {
    private val userViewModel: UserViewModel by viewModel()
    private lateinit var binding: UserFragmentBinding
    private lateinit var userData: User
    val args: UserFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = UserFragmentBinding.inflate(inflater, container, false)

        observeLiveData()
        getUserData(args.userId)

        return binding.root
    }

    private fun observeLiveData() {
        userViewModel.userLiveData.observe(viewLifecycleOwner, Observer { onUserDataReceived(it) })
    }

    private fun onUserDataReceived(userData: User){
        this.userData = userData
        binding.user = userData
    }

    private fun getUserData(userId: Int){
        userViewModel.getUserData(userId)
    }


}
