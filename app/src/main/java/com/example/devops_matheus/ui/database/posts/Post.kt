package com.example.devops_matheus.ui.database.posts

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post_table")
data class Post(
    @PrimaryKey(autoGenerate = true)
    var postId: Long = 0L,

    @ColumnInfo(name = "post_text")
    var postText: String = "",

    @ColumnInfo(name = "post_link")
    var postLink: String = "",

    @ColumnInfo(name = "post_image")
    var postImage: Bitmap? = null,

    @ColumnInfo(name = "post_user")
    var postUser: String = ""
)
