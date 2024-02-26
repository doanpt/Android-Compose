package com.andrew.pokedex.repository

import androidx.paging.PagingSource
import com.andrew.pokedex.data.remote.models.Task
import com.andrew.pokedex.db.TaskDao
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {
    suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    fun getTasksWithPaging(): PagingSource<Int, Task> {
        return taskDao.getTasksWithPaging()
    }
}