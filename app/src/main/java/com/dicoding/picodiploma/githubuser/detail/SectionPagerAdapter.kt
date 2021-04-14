package com.dicoding.picodiploma.githubuser.detail

import android.content.Context
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.picodiploma.githubuser.R
import com.dicoding.picodiploma.githubuser.detail.follower.FollowerFragment
import com.dicoding.picodiploma.githubuser.detail.following.FollowingFragment

class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    private var username : String? = null

    fun setName(name : String){
        username = name
    }

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment : Fragment? = null
        when(position){
            0 -> fragment = FollowerFragment.newInstance(username!!)
            1 -> fragment = FollowingFragment.newInstance(username!!)
        }
        return fragment as Fragment
    }


}