package com.brandon.todo_app.ui.todo.list

enum class TodoListViewType {
    ITEM,
    UNKNOWN
    ;

    companion object {
        fun from(ordinal: Int): TodoListViewType = TodoListViewType.values().find {
            it.ordinal == ordinal
        } ?: UNKNOWN
    }
}