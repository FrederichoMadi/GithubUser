package com.dicoding.picodiploma.githubuser.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.dicoding.picodiploma.githubuser.db.DatabaseContract.GitColumns.Companion.TABLE_NAME
import com.dicoding.picodiploma.githubuser.db.DatabaseContract.GitColumns.Companion._ID
import java.sql.SQLException
import kotlin.jvm.Throws


class GithubHelper(context: Context) {

    companion object{
        private const val DATABASE_TABLE = TABLE_NAME
        private val INSTANCE : GithubHelper? = null

        fun getInstance(context: Context) : GithubHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: GithubHelper(context)
            }

    }

    private lateinit var database : SQLiteDatabase
    private val databaseHelper by lazy {
        DatabaseHelper(context)
    }

    @Throws(SQLException::class)
    fun open(){
        database = databaseHelper.writableDatabase
    }

    fun close(){
        databaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun getAllData() : Cursor {
        return database.query(DATABASE_TABLE,
        null,
        null,
        null,
        null,
        null,
        "$_ID ASC"
            )
    }

    fun insertData(values : ContentValues) : Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun deleteData(id : String) : Int{
        return database.delete(DATABASE_TABLE, "$_ID = $id", null)
    }


}