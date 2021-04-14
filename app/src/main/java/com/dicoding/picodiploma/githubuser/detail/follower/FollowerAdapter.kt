package com.dicoding.picodiploma.githubuser.detail.follower

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.githubuser.R
import com.dicoding.picodiploma.githubuser.UserItems
import com.dicoding.picodiploma.githubuser.databinding.UserItemsBinding

class FollowerAdapter : RecyclerView.Adapter<FollowerAdapter.FollwerViewHolder>(){

    private val mData = ArrayList<UserItems>()

    fun setData(item : ArrayList<UserItems>){
        mData.clear()
        mData.addAll(item)
        notifyDataSetChanged()
    }

    inner class FollwerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollwerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_items, parent, false)
        return FollwerViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollwerViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size
}