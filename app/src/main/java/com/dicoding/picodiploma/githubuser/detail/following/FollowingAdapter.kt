package com.dicoding.picodiploma.githubuser.detail.following

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.githubuser.R
import com.dicoding.picodiploma.githubuser.UserItems
import com.dicoding.picodiploma.githubuser.databinding.UserItemsBinding

class FollowingAdapter : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {

    val mData = ArrayList<UserItems>()

    fun setData(item : ArrayList<UserItems>){
        mData.clear()
        mData.addAll(item)
        notifyDataSetChanged()
    }


    inner class FollowingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = UserItemsBinding.bind(itemView)
        fun bind(userItems: UserItems){
            with(itemView){
                Glide.with(context)
                    .load(userItems.avatar)
                    .centerCrop()
                    .apply(RequestOptions().override(150,150))
                    .into(binding.imgAvatar)
                binding.tvUsername.text = userItems.login
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_items, parent, false)
        return FollowingViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size
}