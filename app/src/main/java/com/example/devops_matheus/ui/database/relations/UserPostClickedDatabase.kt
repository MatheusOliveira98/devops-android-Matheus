package com.example.devops_matheus.ui.database.relations

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.devops_matheus.ui.database.Converters
import com.example.devops_matheus.ui.database.UserPostClicked

@Database(entities = [UserPostClicked::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class UserPostClickedDatabase: RoomDatabase() {
    abstract val userPostDatabaseDao: UserPostClickedDao

    companion object {

        @Volatile
        private var INSTANCE: UserPostClickedDatabase? = null

        fun getInstance(context: Context): UserPostClickedDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserPostClickedDatabase::class.java,
                        "userpostclicked_database"
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