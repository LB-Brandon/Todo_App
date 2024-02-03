package com.brandon.todo_app.ui.todo.content

enum class TodoContentEntryType  {
    CREATE, UPDATE;

    companion object {
        fun from(name: String): TodoContentEntryType = TodoContentEntryType.values().find {
            it.name.uppercase() == name.uppercase()
        } ?: CREATE
    }
}