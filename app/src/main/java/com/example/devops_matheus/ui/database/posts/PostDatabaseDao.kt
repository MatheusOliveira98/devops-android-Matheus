package com.example.devops_matheus.ui.database.posts

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PostDatabaseDao {

    @Insert
    suspend fun insert(post: Post)

    @Update
    suspend fun update(post: Post)

    @Delete
    suspend fun delete(post: Post)

    @Query("SELECT * from post_table WHERE postId = :key")
    suspend fun get(key: Long): Post

    @Query("SELECT * FROM post_table ORDER BY postId DESC")
    suspend fun getAllPosts(): List<Post>

    @Query("SELECT * FROM post_table ORDER BY postId DESC")
    fun getAllPostsLive(): LiveData<List<Post>>

    @Query("SELECT * FROM post_table ORDER BY postId DESC LIMIT 1")
    suspend fun getLastPost(): Post?

    @Query("DELETE FROM post_table")
    suspend fun clear()
}