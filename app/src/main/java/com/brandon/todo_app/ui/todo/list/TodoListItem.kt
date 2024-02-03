package com.brandon.todo_app.ui.todo.list

sealed interface TodoListItem {

    data class Item(
        val id: String?,
        val title: String?,
        val content: String?,
        val isBookmark: Boolean = false,
    ) : TodoListItem
}