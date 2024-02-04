package com.brandon.todo_app.ui.todo.content

enum class TodoContentEntryType  {
    CREATE, UPDATE;

    // 데이터 직렬화를 피하기 위해 ordinal 이나 name 으로 데이터 통신 시 사용
    companion object {
        fun from(name: String): TodoContentEntryType = entries.find {
            it.name.uppercase() == name.uppercase()
        } ?: CREATE
    }
}