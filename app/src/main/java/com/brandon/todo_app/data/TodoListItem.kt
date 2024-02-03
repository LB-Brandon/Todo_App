package com.brandon.todo_app.data

sealed interface TodoListItem {

    data class Item(
        val id: String?,
        val title: String?,
        val content: String?,
        val isBookmark: Boolean = false,
    ) : TodoListItem
}