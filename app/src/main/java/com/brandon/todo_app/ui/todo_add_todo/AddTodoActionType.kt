package com.brandon.todo_app.ui.todo_add_todo

import timber.log.Timber

enum class AddTodoActionType {
    CREATE, UPDATE, DELETE
    ;

    companion object {
        fun Int.convertToEntryType(): AddTodoActionType =
            entries.getOrNull(this) ?: CREATE
    }
}