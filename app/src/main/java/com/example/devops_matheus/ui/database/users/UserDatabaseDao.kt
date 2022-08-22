package com.example.devops_matheus.ui.database.users

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDatabaseDao {

    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * from user_table WHERE userId = :key")
    suspend fun get(key: String): User

    @Query("SELECT * FROM user_table ORDER BY userId DESC")
    suspend fun getAllUsers(): List<User>

    @Query("SELECT * FROM user_table ORDER BY userId DESC")
    fun getAllUsersLive(): LiveData<List<User>>

    @Query("SELECT * FROM user_table ORDER BY userId DESC LIMIT 1")
    suspend fun getLastUser(): User?

    @Query("DELETE FROM user_table")
    suspend fun clear()
}