package com.example.devops_matheus.ui.database.comments

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comment_table")
data class Comment(
    @PrimaryKey(autoGenerate = true)
    var commentId: Long = 0L,

    @ColumnInfo(name = "comment_postId")
    var commentPostId: Long = 0L,

    @ColumnInfo(name = "comment_text")
    var commentText: String = "",

    @ColumnInfo(name = "comment_user")
    var commentUser: String = "",

    @ColumnInfo(name = "comment_is_reaction")
    var commentIsReaction: Boolean = false,

    @ColumnInfo(name = "comment_reaction_id")
    var commentReaction: Long? = 0L
)