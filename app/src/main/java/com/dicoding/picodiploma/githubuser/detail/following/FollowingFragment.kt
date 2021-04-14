package com.dicoding.picodiploma.githubuser.detail.following

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.githubuser.R
import com.dicoding.picodiploma.githubuser.databinding.FragmentFollowerBinding
import com.dicoding.picodiploma.githubuser.databinding.FragmentFollowingBinding
import com.dicoding.picodiploma.githubuser.detail.follower.FollowerFragment
import kotlin.math.log

class FollowingFragment : Fragment() {

    companion object{
        val ARG_USERNAME = "username"

        fun newInstance(login : String) : FollowingFragment{
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, login)
            fragment.arguments = bundle
            return fragment
        }

        private lateinit var followingAdapter: FollowingAdapter
        private lateinit var followingViewModel: FollowingViewModel
        private lateinit var binding : FragmentFollowingBinding

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followingAdapter = FollowingAdapter()
        followingAdapter.notifyDataSetChanged()
        binding.rvFollowing.layoutManager = LinearLayoutManager(context)
        binding.rvFollowing.adapter = followingAdapter

       showFollowing()
    }

    private fun showFollowing(){
        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
        if (arguments != null){
            binding.tvNotFound.visibility = View.GONE
            binding.progresBar.visibility = View.VISIBLE
            val login = arguments?.getString(ARG_USERNAME)
            followingViewModel.setFollowingData(login.toString())
        }
        followingViewModel.getFollowingUser().observe(this, {
            if (it != null ){
                binding.progresBar.visibility = View.GONE
                binding.tvNotFound.visibility = View.GONE
                followingAdapter.setData(it)
            }
        })
    }

}