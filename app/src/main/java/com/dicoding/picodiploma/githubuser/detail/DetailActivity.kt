package com.dicoding.picodiploma.githubuser.detail

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.githubuser.R
import com.dicoding.picodiploma.githubuser.UserItems
import com.dicoding.picodiploma.githubuser.databinding.ActivityDetailBinding
import com.dicoding.picodiploma.githubuser.db.DatabaseContract.GitColumns.Companion.AVATAR_USER
import com.dicoding.picodiploma.githubuser.db.DatabaseContract.GitColumns.Companion.LOGIN_USER
import com.dicoding.picodiploma.githubuser.db.DatabaseContract.GitColumns.Companion._ID
import com.dicoding.picodiploma.githubuser.db.GithubHelper
import com.dicoding.picodiploma.githubuser.db.MappingHelper
import com.dicoding.picodiploma.githubuser.favorite.FavoriteAdapter
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private var statusFavorite = false
    private var detailUser : UserItems? = null
    private var menuItem : Menu? = null
    private var favoriteUser : String? = null
    private lateinit var githubHelper: GithubHelper
    private lateinit var binding : ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var favoriteAdapter: FavoriteAdapter

    companion object{
        const val EXTRA_DATA = "data"
        const val EXTRA_FAVORITE = "favorite"
        @StringRes
        private val TAB_TITLES = intArrayOf(
                R.string.tab_text_1,
                R.string.tab_text_2
        )

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.title = "Detail User"

        githubHelper = GithubHelper.getInstance(this)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteUser = intent.getParcelableExtra(EXTRA_FAVORITE)
        detailUser = intent.getParcelableExtra(EXTRA_DATA)
        Log.d("Check user", "$detailUser")

        detailUser?.let { setDetailUser(it) }

        setTabLayout()
    }

    private fun setTabLayout(){
        val sectionPagerAdapter = SectionPagerAdapter(this)
        if (detailUser != null){
            sectionPagerAdapter.setName(detailUser?.login.toString())
        }
        binding.pager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs, binding.pager){ tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

    }

    private fun setDetailUser(data : UserItems){
        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
                .get(DetailViewModel::class.java)
        with(binding){
            progressBar.visibility = View.VISIBLE
            tvPengikut.visibility = View.GONE
            tvMengikuti.visibility = View.GONE
            tvUsername.visibility = View.GONE
            textView2.visibility = View.GONE
            textView.visibility = View.GONE
            imgAvatar.visibility = View.GONE
        }

        detailViewModel.setDetailUser(data.login)
        detailViewModel.getUser().observe(this, { userItem ->
            if (userItem != null) {
                binding.tvUsername.text = userItem.login
                binding.tvMengikuti.text = userItem.following.toString()
                binding.tvPengikut.text = userItem.followers.toString()

                Glide.with(this)
                        .load(userItem.avatar)
                        .centerCrop()
                        .apply(RequestOptions().override(150, 150))
                        .into(binding.imgAvatar)

                with(binding){
                    progressBar.visibility = View.GONE
                    tvPengikut.visibility = View.VISIBLE
                    tvMengikuti.visibility = View.VISIBLE
                    tvUsername.visibility = View.VISIBLE
                    textView2.visibility = View.VISIBLE
                    textView.visibility = View.VISIBLE
                    imgAvatar.visibility = View.VISIBLE
                }
                Log.d("userDetail", "$userItem")
            }
        })
    }

    private fun setStatusFavorite(menu : Menu){
        if (statusFavorite){
            menu.getItem(0).icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_24)
        } else {
            menu.getItem(0).icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border_24)
        }
    }

    private fun setFavorite(){
        if (statusFavorite){
            detailUser.let {
                githubHelper.deleteData("${it?.id}")
                Log.d("Delete Favorite", "Delete Favorite")
                Toast.makeText(this, "Delete favorite", Toast.LENGTH_SHORT).show()
                statusFavorite = false
                setStatusFavorite(menuItem!!)
            }
        } else {
            val values = ContentValues()
            values.put(_ID, detailUser?.id)
            values.put(LOGIN_USER, detailUser?.login)
            values.put(AVATAR_USER, detailUser?.avatar)
            githubHelper.insertData(values)
            Log.d("Add Favorite", "Add Favorite")
            Toast.makeText(this, "Add favorite", Toast.LENGTH_SHORT).show()
            statusFavorite = true
            setStatusFavorite(menuItem!!)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_items, menu)
        setStatusFavorite(menu!!)
        this.menuItem = menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings){
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        } else if (item.itemId == R.id.action_favorite){
            setFavorite()
        }
        return super.onOptionsItemSelected(item)
    }
}