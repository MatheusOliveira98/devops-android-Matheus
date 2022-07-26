package com.example.devops_matheus.ui.database.posts

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.devops_matheus.ui.database.Converters
import java.security.AccessControlContext


@Database(entities = [Post::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PostDatabase: RoomDatabase() {
    abstract val postDatabaseDao: PostDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: PostDatabase? = null

        fun getInstance(context: Context): PostDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PostDatabase::class.java,
                        "post_database"
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