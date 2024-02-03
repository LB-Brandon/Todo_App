package com.brandon.todo_app.ui.todo.content

data class TodoContentUiState(
    val title: String?,
    val content: String?,
    val button: TodoContentButtonUiState?
) {
    companion object {

        fun init() = TodoContentUiState(
            title = null,
            content = null,
            button = null
        )
    }
}

sealed interface TodoContentButtonUiState {
    object Create : TodoContentButtonUiState
    object Update : TodoContentButtonUiState
}