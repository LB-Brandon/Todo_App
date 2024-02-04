package com.brandon.todo_app.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.brandon.todo_app.data.TodoEntity
import com.brandon.todo_app.data.TodoListItem
import com.brandon.todo_app.util.SingleLiveEvent

class BookmarkViewModel: ViewModel() {

    private val _event: SingleLiveEvent<BookmarkListEvent> = SingleLiveEvent()
    val event: LiveData<BookmarkListEvent> get() = _event


    fun onClickItem(item: TodoListItem) {
        _event.value = when (item) {
            is TodoListItem.Item -> BookmarkListEvent.OpenContent(
                TodoEntity(
                    id = item.id,
                    title = item.title,
                    content = item.content
                )
            )
        }
    }

}