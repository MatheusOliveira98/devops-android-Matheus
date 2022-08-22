package com.example.devops_matheus.ui.database.quote

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.devops_matheus.ui.database.Converters
import java.security.AccessControlContext


@Database(entities = [Quote::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class QuoteDatabase: RoomDatabase() {
    abstract val quoteDatabaseDao: QuoteDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: QuoteDatabase? = null

        fun getInstance(context: Context): QuoteDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        QuoteDatabase::class.java,
                        "quote_database"
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