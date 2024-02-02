package com.brandon.todo_app.ui.todo_add_todo

data class AddTodoUiState(
    val entryType: AddTodoEntryType?,
    val title: String?,
    val description: String?,
    val isSubmitButtonEnabled: Boolean?
){
    companion object {
        fun init() = AddTodoUiState(
            entryType = null,
            title = null,
            description = null,
            isSubmitButtonEnabled = false
        )
    }
}

