package com.usn.ultimatesportnews.data.local

import android.content.Context
import androidx.room.*
import com.usn.ultimatesportnews.data.local.dao.NewsDao
import com.usn.ultimatesportnews.data.local.entities.NewsStoryEntity
import com.usn.ultimatesportnews.data.local.entities.NewsVideoEntity


@Database(
    entities = [
        NewsStoryEntity::class,
        NewsVideoEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class DatabaseUsn : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {
        private lateinit var db: DatabaseUsn

        fun init(context: Context) {
            db = Room.databaseBuilder(context, DatabaseUsn::class.java, "usn")
                .fallbackToDestructiveMigration()
                .build()
        }

        fun get() = db
    }
}