package com.dicoding.picodiploma.githubuser.db

import android.database.Cursor
import com.dicoding.picodiploma.githubuser.UserItems
import com.dicoding.picodiploma.githubuser.db.DatabaseContract.GitColumns.Companion.AVATAR_USER
import com.dicoding.picodiploma.githubuser.db.DatabaseContract.GitColumns.Companion.LOGIN_USER
import com.dicoding.picodiploma.githubuser.db.DatabaseContract.GitColumns.Companion._ID

object MappingHelper {
    fun mapCursorToArrayList(cursor : Cursor) : ArrayList<UserItems>{
        val userList = ArrayList<UserItems>()

        cursor.apply {
            while (moveToNext()){
                val id = getLong(getColumnIndexOrThrow(_ID))
                val login = getString(getColumnIndexOrThrow(LOGIN_USER))
                val avatar = getString(getColumnIndexOrThrow(AVATAR_USER))
                userList.add(UserItems(id = id, login = login, avatar = avatar))
            }
        }
        return userList
    }
}