package com.andrew.pokedex.db

import androidx.paging.PagingSource
import androidx.room.*
import com.andrew.pokedex.data.remote.models.Task

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Query("SELECT * FROM task")
    fun getTasksWithPaging(): PagingSource<Int, Task>
}