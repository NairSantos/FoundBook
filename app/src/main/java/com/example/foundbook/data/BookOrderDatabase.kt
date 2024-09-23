package com.example.foundbook.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Order::class], version = 1, exportSchema = false)
abstract class BookOrderDatabase : RoomDatabase() {
    abstract fun orderDao(): OrderDao

    companion object {
        @Volatile
        private var Instance: BookOrderDatabase? = null

        fun getDatabase(context: Context): BookOrderDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, BookOrderDatabase::class.java, "order_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        Instance = it
                    }
            }
        }
    }
}