package com.brandon.todo_app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brandon.todo_app.data.TodoEntity
import com.brandon.todo_app.data.TodoListItem
import com.brandon.todo_app.ui.todo.content.TodoContentActionType
import timber.log.Timber

class TodoMainViewModel : ViewModel() {
    private val _todoList = MutableLiveData<List<TodoListItem.Item>>()
    val todoItemList: LiveData<List<TodoListItem.Item>> get() = _todoList


    fun updateTodoItem(entryType: TodoContentActionType? = null, entity: TodoEntity?) {
        entryType ?: run {
            Timber.e("Can't find entry type")
            return
        }
        entity ?: run {
            Timber.e("Can't find updated entity")
            return
        }

        val currentList = todoItemList.value.orEmpty()

        when (entryType) {
            TodoContentActionType.UPDATE -> {
                // Find the item to update
                val updatedList = currentList.map { item ->
                    if (item.id == entity.id) {
                        // Update the item if the IDs match
                        item.copy(title = entity.title, content = entity.content)
                    } else {
                        item
                    }
                }
                _todoList.value = updatedList
                Timber.d("Update TodoEntity: $entity")
            }

            TodoContentActionType.DELETE -> {
                // Remove the item with matching ID
                val updatedList = currentList.filter { item ->
                    item.id != entity.id
                }
                _todoList.value = updatedList
                Timber.d("Delete TodoEntity: $entity")
            }

            TodoContentActionType.CREATE -> {
                _todoList.value = (todoItemList.value ?: emptyList()) + createTodoItem(entity)
                Timber.d("Saved TodoEntity into SharedViewModel: $entity")
            }
        }
    }

    fun toggleBookmark(entity: TodoListItem){
        val currentList = todoItemList.value.orEmpty()

        when(entity){
            is TodoListItem.Item -> {
                val updatedList = currentList.map { item ->
                    if (entity.id == item.id) {
                        // Update the item if the IDs match
                        item.copy(isBookmarked = item.isBookmarked.not()) // 구현
                    } else {
                        item
                    }
                }
                _todoList.value = updatedList
                Timber.d("Toggle bookmark")
            }
        }
    }


    private fun createTodoItem(entity: TodoEntity): TodoListItem.Item {
        return TodoListItem.Item(
            id = entity.id,
            title = entity.title,
            content = entity.content
        )
    }


}