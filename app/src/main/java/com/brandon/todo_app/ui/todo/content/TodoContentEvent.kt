package com.brandon.todo_app.ui.todo.content

sealed interface TodoContentEvent {
    data class Create(
        val id: String,
        val title: String,
        val content: String,
    ) : TodoContentEvent

    data class Update(
        val id: String?,
        val title: String,
        val content: String,
    ) : TodoContentEvent

    data class Delete(
        val id: String?,
    ) : TodoContentEvent
}