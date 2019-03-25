package com.example.tmdbkotlin.util.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

//import static java.security.AccessController.getContext;


class UserDbUtil(private val context: Context) {

    private var db: SQLiteDatabase? = null

    fun getWriteDB() {
        db = UserDbHelper(context).writableDatabase
    }

    fun getReadDB() {
        db = UserDbHelper(context).readableDatabase
    }

    fun insert(fullname: String, username: String, userid: Int): Long {
        val values = ContentValues()

        values.put(UserInfoContract.UserEntry.COLUMN_NAME_FULL_NAME, fullname)
        values.put(UserInfoContract.UserEntry.COLUMN_NAME_USER_NAME, username)
        values.put(UserInfoContract.UserEntry.COLUMN_NAME_USER_ID, userid)

        return db!!.insert(
            UserInfoContract.UserEntry.TABLE_NAME, // don't group the rows
            null, values
        )

    }

    fun read(username: String): String? {

        val projection = arrayOf(
            BaseColumns._ID,
            UserInfoContract.UserEntry.COLUMN_NAME_FULL_NAME,
            UserInfoContract.UserEntry.COLUMN_NAME_USER_NAME,
            UserInfoContract.UserEntry.COLUMN_NAME_USER_ID
        )

        // Filter results WHERE "title" = 'My Title'
        val selection = UserInfoContract.UserEntry.COLUMN_NAME_USER_NAME + " = ?"
        val selectionArgs = arrayOf(username)

        // How you want the results sorted in the resulting Cursor
        val sortOrder = BaseColumns._ID + " DESC"

        val cursor = db!!.query(
            UserInfoContract.UserEntry.TABLE_NAME, // The table to query
            projection, // The array of columns to return (pass null to get all)
            selection, // The columns for the WHERE clause
            selectionArgs, null, null, // don't filter by row groups
            sortOrder               // The sort order
        )

        cursor.moveToNext()

        var keyUsername : String? = null

        //temporary value fetch
        if ( cursor.count != 0  )
        keyUsername = cursor.getString(2)

        cursor.close()

        return keyUsername
    }
}
