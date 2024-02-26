package com.andrew.pokedex.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.andrew.pokedex.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    val tasksPaging = Pager(
        PagingConfig(pageSize = 20)
    ) {
        taskRepository.getTasksWithPaging()
    }.flow.cachedIn(viewModelScope)

}