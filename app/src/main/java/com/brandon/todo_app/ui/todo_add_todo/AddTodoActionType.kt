package com.brandon.todo_app.ui.todo_add_todo

enum class AddTodoActionType {
    CREATE, UPDATE, DELETE
    ;

    companion object {
        fun Int.convertToActionType(): AddTodoActionType =
            entries.getOrNull(this) ?: CREATE
    }
}