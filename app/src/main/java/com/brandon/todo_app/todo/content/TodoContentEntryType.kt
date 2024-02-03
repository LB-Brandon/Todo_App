package com.brandon.todo_app.todo.content

enum class TodoContentEntryType  {
    CREATE, UPDATE, DELETE;

    companion object {
        fun from(name: String): TodoContentEntryType = TodoContentEntryType.values().find {
            it.name.uppercase() == name.uppercase()
        } ?: CREATE
    }
}