package com.brandon.todo_app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brandon.todo_app.data.TodoModel
import timber.log.Timber

class MainSharedViewModel : ViewModel() {
    private val _todoItemList: MutableLiveData<List<TodoModel>> = MutableLiveData()
    val todoItemList: LiveData<List<TodoModel>> = _todoItemList


    fun addTodoItem(todoModel: TodoModel?) {
        todoModel?: return
        _todoItemList.value = (todoItemList.value ?: emptyList()) + todoModel
        Timber.d("Add todoModel: $todoModel")
    }

}
