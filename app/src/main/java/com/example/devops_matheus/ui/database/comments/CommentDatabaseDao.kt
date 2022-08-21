package com.example.devops_matheus.ui.database.comments

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.devops_matheus.ui.database.comments.Comment

@Dao
interface CommentDatabaseDao {

    @Insert
    suspend fun insert(comment: Comment)

    @Update
    suspend fun update(comment: Comment)

    @Delete
    suspend fun delete(comment: Comment)

    @Query("SELECT * from comment_table WHERE commentId = :key")
    suspend fun get(key: Long): Comment

    @Query("SELECT * FROM comment_table WHERE comment_postId = :postId ORDER BY commentId DESC")
    suspend fun getAllCommentsFromPost(postId: Long): List<Comment>

    @Query("SELECT * FROM comment_table WHERE comment_postId = :postId ORDER BY commentId DESC")
    fun getAllCommentsFromPostLive(postId: Long): LiveData<List<Comment>>

    @Query("SELECT * FROM comment_table WHERE comment_postId = :postId ORDER BY commentId DESC LIMIT 1")
    suspend fun getLastCommentFromPost(postId: Long): Comment?

    @Query("SELECT * FROM comment_table WHERE comment_postId = :postId AND comment_reaction = :commentId ORDER BY commentId DESC")
    suspend fun getReactionsFromComment(postId: Long, commentId: Long): List<Comment>

    @Query("DELETE FROM comment_table")
    suspend fun clear()
}