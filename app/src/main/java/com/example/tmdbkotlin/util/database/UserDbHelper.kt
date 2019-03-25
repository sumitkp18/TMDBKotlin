package com.example.tmdbkotlin.util.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class UserDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(SQL_CREATE_ENTRIES)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)

    }


    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {

        val DATABASE_VERSION = 1
        val DATABASE_NAME = "userInfo.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${UserInfoContract.UserEntry.TABLE_NAME}  (" +
                    "${BaseColumns._ID}   INTEGER PRIMARY KEY," +
                    "${UserInfoContract.UserEntry.COLUMN_NAME_FULL_NAME} TEXT," +
                    "${UserInfoContract.UserEntry.COLUMN_NAME_USER_NAME}  TEXT,"+
                    "${UserInfoContract.UserEntry.COLUMN_NAME_USER_ID} INTEGER)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + UserInfoContract.UserEntry.TABLE_NAME
    }
}
