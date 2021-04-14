package com.dicoding.picodiploma.githubuser.db

import android.provider.BaseColumns

object DatabaseContract {

    internal class GitColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "git_user"
            const val _ID = "_id"
            const val LOGIN_USER = "login_user"
            const val AVATAR_USER = "avatar_user"
        }
    }
}