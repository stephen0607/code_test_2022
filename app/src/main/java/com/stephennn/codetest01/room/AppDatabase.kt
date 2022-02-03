package com.stephennn.codetest01.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stephennn.codetest01.model.CurrencyInfo

@Database(entities = [CurrencyInfo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyDAO(): CurrencyDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "text"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}