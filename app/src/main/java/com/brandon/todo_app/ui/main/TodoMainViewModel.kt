package com.brandon.todo_app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brandon.todo_app.data.TodoEntity
import timber.log.Timber

class TodoMainViewModel : ViewModel() {
    private val _sharedTodoList = MutableLiveData<List<TodoEntity>>()
    val sharedTodoList: LiveData<List<TodoEntity>> get() = _sharedTodoList

    fun saveTodoModel(todoModel: TodoEntity?){
        todoModel ?: run {
            Timber.e("Can't find the TodoModel")
            return
        }
        _sharedTodoList.value = (sharedTodoList.value ?: emptyList()) + todoModel
        Timber.d("Saved TodoModel to SharedViewModel: $todoModel")
    }

}