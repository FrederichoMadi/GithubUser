package com.dicoding.picodiploma.githubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class MainViewModel : ViewModel() {
    private val listUserItems = MutableLiveData<ArrayList<UserItems>>()

    fun setSearhUser(username : String){
        val listUser = ArrayList<UserItems>()

        val url = "https://api.github.com/search/users?q=$username"
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d("Check", result)
                try {
                    val responseObject = JSONObject(result)
                    val items = responseObject.getJSONArray("items")

                    for(i in 0 until items.length()){
                        val user = items.getJSONObject(i)
                        val userItems = UserItems()
                        userItems.id = user.getLong("id")
                        userItems.login = user.getString("login")
                        userItems.avatar = user.getString("avatar_url")
                        listUser.add(userItems)
                    }

                    listUserItems.postValue(listUser)
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

    fun getData() : LiveData<ArrayList<UserItems>>{
        return listUserItems
    }
}