package com.dicoding.picodiploma.githubuser

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserItems (
    var id : Long = 0,
    var login : String? = null,
    var avatar : String? = null,
    var followers : Int = 0,
    var following : Int = 0,
    var bio : String? = null,
    var company : String? = null,
    var email : String? = null,
) : Parcelable