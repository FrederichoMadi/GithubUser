package com.dicoding.picodiploma.githubuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.TransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeTransition
import com.dicoding.picodiploma.githubuser.databinding.UserItemsBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    interface OnItemClickCallback{
        fun onItemClicked(data : UserItems)
    }

    private var onItemClickCallback : OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    private val mData = ArrayList<UserItems>()

    fun setData(items : ArrayList<UserItems>){
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = UserItemsBinding.bind(itemView)
        fun bind(userItems: UserItems){
            with(itemView){
                Glide.with(context)
                    .load(userItems.avatar)
                    .centerCrop()
                    .apply(RequestOptions().override(150,150))
                    .into(binding.imgAvatar)

                binding.tvUsername.text = userItems.login

                this.setOnClickListener { onItemClickCallback?.onItemClicked(userItems) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_items, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size
}