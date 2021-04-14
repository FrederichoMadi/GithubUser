package com.dicoding.picodiploma.githubuser.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.githubuser.R
import com.dicoding.picodiploma.githubuser.UserItems
import com.dicoding.picodiploma.githubuser.databinding.ActivityFavoriteBinding
import com.dicoding.picodiploma.githubuser.db.GithubHelper
import com.dicoding.picodiploma.githubuser.db.MappingHelper
import com.dicoding.picodiploma.githubuser.detail.DetailActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter
    private lateinit var githubHelper: GithubHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = FavoriteAdapter()
        binding.rcView.layoutManager = LinearLayoutManager(this)
        binding.rcView.adapter = adapter

        moveToDetail()

        showAllData()

    }

    fun moveToDetail(){
        adapter.setOnItemClickCallback( object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserItems) {
                val favIntent = Intent(this@FavoriteActivity, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_DATA, data)
                    putExtra(DetailActivity.EXTRA_FAVORITE, "favorite")
                }
                startActivity(favIntent)

            }

        })
    }

    fun showAllData(){
        GlobalScope.launch(Dispatchers.Main) {
            githubHelper = GithubHelper.getInstance(this@FavoriteActivity)
            githubHelper.open()
            val deferredGit = async {
                val cursor = githubHelper.getAllData()
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val notes = deferredGit.await()
            if (notes.size > 0 ){
                adapter.favoriteList = notes
            } else {
                adapter.favoriteList = ArrayList()
                Snackbar.make(binding.rcView, "Tidak ada Data", Snackbar.LENGTH_SHORT).show()
            }
            githubHelper.close()
        }
    }
}