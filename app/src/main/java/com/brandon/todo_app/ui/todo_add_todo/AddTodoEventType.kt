package com.brandon.todo_app.ui.todo_add_todo

sealed interface AddTodoEventType {
    object ClickAddButton : AddTodoEventType
    object ClickUpdateButton : AddTodoEventType
    object ClickDeleteButton : AddTodoEventType
}