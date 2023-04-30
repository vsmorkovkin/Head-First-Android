package com.hfad.tasks

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class TaskViewModel(val dao: TaskDao) : ViewModel() {
    var newTaskName = MutableLiveData("")

    private val tasks = dao.getAll()
    val tasksString = tasks.map { tasks ->
        formatTasks(tasks)
    }

    fun addTask() {
        viewModelScope.launch {
            val task = Task()
            task.taskName = newTaskName.value!!
            dao.insert(task)
        }
    }

    fun formatTasks(tasks: List<Task>): String {
        return tasks.fold("") {
            str, item -> str + '\n' + formatTask(item)
        }
    }

    fun formatTask(task: Task): String {
        var str = "ID: ${task.taskId}"
        str += '\n' + "Name: ${task.taskName}"
        str += '\n' + "Complete: ${task.taskDone}" + '\n'
        return str
    }
}