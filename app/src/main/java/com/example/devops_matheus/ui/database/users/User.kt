package com.example.devops_matheus.ui.database.users

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = false)
    var userId: String = "",

    @ColumnInfo(name = "user_name")
    var userName: String? = null,

    @ColumnInfo(name = "user_avatar")
    var userAvatar: Bitmap? = null,

    @ColumnInfo(name = "user_role")
    var userRole: String? = null
)