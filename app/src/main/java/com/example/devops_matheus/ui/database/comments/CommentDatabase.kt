package com.example.devops_matheus.ui.database.comments

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Comment::class], version = 1, exportSchema = false)
abstract class CommentDatabase: RoomDatabase() {

    abstract val commentDatabaseDao: CommentDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: CommentDatabase? = null

        fun getInstance(context: Context): CommentDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CommentDatabase::class.java,
                        "comment_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}