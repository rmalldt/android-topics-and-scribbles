package com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase6

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AndroidVersionEntity::class], version = 1, exportSchema = false)
abstract class AndroidVersionDatabase : RoomDatabase() {

    abstract fun androidVersionDao(): AndroidVersionDao

    companion object {
        @Volatile
        private var INSTANCE: AndroidVersionDatabase? = null

        fun getInstance(context: Context): AndroidVersionDatabase {
            return INSTANCE ?: synchronized(AndroidVersionDatabase::class) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AndroidVersionDatabase::class.java,
                    "androidversions.db"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}