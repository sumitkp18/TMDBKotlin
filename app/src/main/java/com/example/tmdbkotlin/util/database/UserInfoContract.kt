package com.example.tmdbkotlin.util.database

import android.provider.BaseColumns

object UserInfoContract {

    object UserEntry : BaseColumns {
            const val TABLE_NAME = "user_info"
            const val COLUMN_NAME_USER_NAME = "full_name"
            const val COLUMN_NAME_FULL_NAME = "user_name"
            const val COLUMN_NAME_USER_ID = "user_id"
    }
}
