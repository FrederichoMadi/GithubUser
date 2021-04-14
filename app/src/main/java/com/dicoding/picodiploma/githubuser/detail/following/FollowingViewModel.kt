package com.dicoding.picodiploma.githubuser.detail.following

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.githubuser.UserItems
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import java.lang.Exception

class FollowingViewModel : ViewModel() {
    private val listData = MutableLiveData<ArrayList<UserItems>>()

    fun setFollowingData(username : String?){

        val listItems = ArrayList<UserItems>()

        val url =  "https://api.github.com/users/$username/following"
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                val result = String(responseBody)
                try {
                    val responseArray = JSONArray(result)
                    for (i in 0 until responseArray.length()){
                        val user = responseArray.getJSONObject(i)
                        val mUser = UserItems()
                        mUser.login = user.getString("login")
                        mUser.avatar = user.getString("avatar_url")
                        listItems.add(mUser)
                    }

                    listData.postValue(listItems)
                }catch (e : Exception){
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable) {
                Log.d("onFailure", error.message.toString() )
            }

        })

    }

    fun getFollowingUser() : LiveData<ArrayList<UserItems>> {
        return listData
    }
}