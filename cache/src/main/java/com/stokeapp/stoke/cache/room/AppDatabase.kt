package com.stokeapp.stoke.cache.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.stokeapp.stoke.cache.room.model.ToBeDeleted

@Database(entities = [
    ToBeDeleted::class
], version = 1)
abstract class AppDatabase : RoomDatabase() {
}