package com.brandon.todo_app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brandon.todo_app.data.TodoEntity
import com.brandon.todo_app.data.TodoListItem
import com.brandon.todo_app.ui.todo.content.TodoContentActionType
import com.brandon.todo_app.ui.todo.content.TodoContentEntryType
import timber.log.Timber

class TodoMainViewModel : ViewModel() {
    private val _sharedTodoList = MutableLiveData<List<TodoListItem.Item>>()
    val todoItemList: LiveData<List<TodoListItem.Item>> get() = _sharedTodoList

    fun saveTodoItem(todoEntity: TodoEntity?){
        todoEntity ?: run {
            Timber.e("Can't find the TodoModel")
            return
        }
        _sharedTodoList.value = (todoItemList.value ?: emptyList()) + createTodoItem(todoEntity)
        Timber.d("Saved TodoModel to SharedViewModel: $todoEntity")
    }

    fun updateTodoItem(entryType: TodoContentActionType, ){

    }

    private fun createTodoItem(entity: TodoEntity): TodoListItem.Item {
        return TodoListItem.Item(
            id = entity.id,
            title = entity.title,
            content = entity.content
        )
    }


}