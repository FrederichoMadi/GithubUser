package com.dicoding.picodiploma.githubuser.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.githubuser.R
import com.dicoding.picodiploma.githubuser.UserItems
import com.dicoding.picodiploma.githubuser.databinding.UserItemsBinding

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    interface OnItemClickCallback{
        fun onItemClicked(data : UserItems)
    }

    private var onItemClickCallback : OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    var favoriteList = ArrayList<UserItems>()
        set(list) {
            if (list.size > 0) {
                this.favoriteList.clear()
            }
            this.favoriteList.addAll(list)
            notifyDataSetChanged()
        }

    fun addItem(user: UserItems){
        favoriteList.add(user)
        notifyItemInserted(favoriteList.size - 1)
    }

    fun removeItem(position : Int){
        favoriteList.removeAt(position)
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = UserItemsBinding.bind(itemView)

        fun bind(user : UserItems){
            Glide.with(itemView.context)
                .load(user.avatar)
                .circleCrop()
                .apply(RequestOptions().override(150,150))
                .into(binding.imgAvatar)
            binding.tvUsername.text = user.login

            itemView.setOnClickListener { onItemClickCallback?.onItemClicked(user) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_items, parent , false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favoriteList[position])
    }

    override fun getItemCount(): Int = favoriteList.size
}