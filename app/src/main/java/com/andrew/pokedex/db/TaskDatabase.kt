package com.andrew.pokedex.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.andrew.pokedex.data.remote.models.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

//exportSchema true will let room export data base schema to a folder, it is good for history tracking
@Database(entities = [Task::class], exportSchema = false, version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}