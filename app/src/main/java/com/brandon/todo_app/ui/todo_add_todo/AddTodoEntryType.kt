package com.brandon.todo_app.ui.todo_add_todo

import timber.log.Timber

enum class AddTodoEntryType {
    CREATE, UPDATE
    ;

    companion object {
        fun Int.convertToEntryType(): AddTodoEntryType =
            AddTodoEntryType.values().firstOrNull {
                it.ordinal == this
            } ?: run {
                Timber.e("Can't find the Entry Type")
                CREATE
            }
    }
}