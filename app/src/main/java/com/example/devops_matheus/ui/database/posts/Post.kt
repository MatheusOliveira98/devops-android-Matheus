package com.example.devops_matheus.ui.database.posts

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
    var postLink: String = ""

    /*
    TODO: foto
     */
)
