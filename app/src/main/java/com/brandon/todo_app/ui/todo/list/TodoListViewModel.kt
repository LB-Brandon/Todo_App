package com.brandon.todo_app.ui.todo.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.brandon.todo_app.data.TodoEntity
import com.brandon.todo_app.data.TodoListItem
import com.brandon.todo_app.ui.todo.content.TodoContentEntryType
import com.brandon.todo_app.util.SingleLiveEvent

class TodoListViewModel : ViewModel() {

    private val _event: SingleLiveEvent<TodoListEvent> = SingleLiveEvent()
    val event: LiveData<TodoListEvent> get() = _event



    fun updateTodoItem(entryType: TodoContentEntryType?, entity: TodoEntity?) {

    }

    fun onClickItem(position: Int, item: TodoListItem) {

    }


}
