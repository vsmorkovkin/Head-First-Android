package com.hfad.tasks

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class TaskViewModel(val dao: TaskDao) : ViewModel() {
    var newTaskName = MutableLiveData("")

    val tasks = dao.getAll()

    fun addTask() {
        viewModelScope.launch {
            if (newTaskName.value!!.isEmpty()) return@launch

            val task = Task()
            task.taskName = newTaskName.value!!
            dao.insert(task)
        }
    }

}