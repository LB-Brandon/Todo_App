package com.brandon.todo_app.ui.todo_add_todo

data class AddTodoUiState(
    val title: String?,
    val description: String?,
    val isSubmitButtonEnabled: Boolean?,
    val entryType: AddTodoActionType?
){
    companion object {
        fun init() = AddTodoUiState(
            title = null,
            description = null,
            isSubmitButtonEnabled = false,
            entryType = null
        )
    }
}

