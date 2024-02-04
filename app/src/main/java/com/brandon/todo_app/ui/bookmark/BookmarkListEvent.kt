package com.brandon.todo_app.ui.bookmark

import com.brandon.todo_app.data.TodoEntity

sealed interface BookmarkListEvent {

    data class OpenContent(
        val item: TodoEntity
    ) : BookmarkListEvent
}