package com.dicoding.picodiploma.githubuser.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.githubuser.UserItems
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class DetailViewModel : ViewModel() {
    val detailUser = MutableLiveData<UserItems>()

    fun setDetailUser(login: String?){
        val url = "https://api.github.com/users/$login"
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result)
                    val mUser = UserItems()
                    mUser.login = responseObject.getString("login")
                    mUser.avatar = responseObject.getString("avatar_url")
                    mUser.followers = responseObject.getInt("followers")
                    mUser.following = responseObject.getInt("following")
                    mUser.bio = responseObject.getString("bio")
                    mUser.company = responseObject.getString("company")
                    mUser.email = responseObject.getString("email")
                    detailUser.postValue(mUser)
                } catch (e : Exception){
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }

        })
    }

    fun getUser() : LiveData<UserItems>{
        return detailUser
    }
}