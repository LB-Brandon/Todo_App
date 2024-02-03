package com.brandon.todo_app.ui.todo_add_todo

import com.brandon.todo_app.data.TodoModel

sealed interface AddTodoEventResultType {

    data class AddResult(val todoModel: TodoModel?) : AddTodoEventResultType

    data class UpdateResult(val todoModel: TodoModel?) : AddTodoEventResultType

    data class DeleteResult(val todoModel: TodoModel?) : AddTodoEventResultType
}