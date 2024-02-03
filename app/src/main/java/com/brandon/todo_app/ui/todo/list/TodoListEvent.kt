package com.brandon.todo_app.ui.todo.list

import com.brandon.todo_app.data.TodoEntity

sealed interface TodoListEvent {

    data class OpenContent(
        val position: Int,
        val item: TodoEntity
    ) : TodoListEvent
}