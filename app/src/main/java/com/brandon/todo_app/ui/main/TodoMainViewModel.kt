package com.brandon.todo_app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brandon.todo_app.data.TodoEntity
import com.brandon.todo_app.data.TodoListItem
import com.brandon.todo_app.ui.todo.content.TodoContentActionType
import timber.log.Timber

class TodoMainViewModel : ViewModel() {
    private val _sharedTodoList = MutableLiveData<List<TodoListItem.Item>>()
    val sharedTodoItemList: LiveData<List<TodoListItem.Item>> get() = _sharedTodoList


    fun updateTodoItem(entryType: TodoContentActionType? = null, entity: TodoEntity?) {
        entryType ?: run {
            Timber.e("Can't find entry type")
            return
        }
        entity ?: run {
            Timber.e("Can't find updated entity")
            return
        }

        val currentList = sharedTodoItemList.value.orEmpty()

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
                _sharedTodoList.value = updatedList
                Timber.d("Update TodoEntity: $entity")
            }

            TodoContentActionType.DELETE -> {
                // Remove the item with matching ID
                val updatedList = currentList.filter { item ->
                    item.id != entity.id
                }
                _sharedTodoList.value = updatedList
                Timber.d("Delete TodoEntity: $entity")
            }

            TodoContentActionType.CREATE -> {
                _sharedTodoList.value = (sharedTodoItemList.value ?: emptyList()) + createTodoItem(entity)
                Timber.d("Saved TodoEntity into SharedViewModel: $entity")
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