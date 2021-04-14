package com.dicoding.picodiploma.githubuser.detail.follower

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.githubuser.R
import com.dicoding.picodiploma.githubuser.databinding.FragmentFollowerBinding
import com.dicoding.picodiploma.githubuser.detail.following.FollowingFragment

class FollowerFragment : Fragment() {

    companion object{
        const val ARG_USERNAME = "username"

        fun newInstance(username : String) : FollowerFragment{
            val fragment = FollowerFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }

    }

    private lateinit var binding: FragmentFollowerBinding
    private lateinit var adapter: FollowerAdapter
    private lateinit var followerVIewModel: FollowerVIewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FollowerAdapter()
        binding.rvFollower.layoutManager = LinearLayoutManager(context)
        binding.rvFollower.adapter = adapter
        adapter.notifyDataSetChanged()

        showFollower()
    }

    private fun showFollower(){
        followerVIewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowerVIewModel::class.java)
        if (arguments != null){
            binding.progresBar.visibility = View.VISIBLE
            binding.tvNotFound.visibility = View.GONE
            val username = arguments?.getString(ARG_USERNAME)
            followerVIewModel.setFollowerData(username.toString())
        }
        followerVIewModel.getDetailUser().observe(this, {
            if (it != null){
                binding.progresBar.visibility = View.GONE
                binding.tvNotFound.visibility = View.GONE
                adapter.setData(it)
            }
        })
    }
}