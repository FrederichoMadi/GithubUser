package com.dicoding.picodiploma.githubuser.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.dicoding.picodiploma.githubuser.db.DatabaseContract.GitColumns.Companion.AVATAR_USER
import com.dicoding.picodiploma.githubuser.db.DatabaseContract.GitColumns.Companion.LOGIN_USER
import com.dicoding.picodiploma.githubuser.db.DatabaseContract.GitColumns.Companion.TABLE_NAME
import com.dicoding.picodiploma.githubuser.db.DatabaseContract.GitColumns.Companion._ID

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION ) {

    companion object{
        private const val DATABASE_NAME = "github"

        private const val DATABASE_VERSION = 1

        private const val QUERY_CREATE_TABLE = """CREATE TABLE $TABLE_NAME (
            $_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $LOGIN_USER TEXT NOT NULL,
            $AVATAR_USER TEXT NOT NULL)
        """
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(QUERY_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}