package com.brandon.todo_app.ui.todo_add_todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brandon.todo_app.data.TodoModel
import timber.log.Timber

class AddTodoViewModel : ViewModel() {

    private val _entryEntity: MutableLiveData<TodoModel?> = MutableLiveData()
    val entryEntity: LiveData<TodoModel?> = _entryEntity

    private var _entryType: MutableLiveData<AddTodoActionType> = MutableLiveData()
    val entryType: LiveData<AddTodoActionType> = _entryType

    private val _uiState: MutableLiveData<AddTodoUiState> = MutableLiveData(AddTodoUiState.init())
    val uiState: LiveData<AddTodoUiState> get() = _uiState

    private val _result: MutableLiveData<AddTodoEventResultType> = MutableLiveData()
    val result: LiveData<AddTodoEventResultType> = _result




    // 라이브 데이터 값 노출
    private val _title: String? get() = _uiState.value?.title
    private val _description: String? get() = _uiState.value?.description
    private val _id: String? get() = _entryEntity.value?.id


    fun setEntryEntity(entryEntity: TodoModel?) {
        _entryEntity.value = entryEntity
        val isCreateType = entryEntity == null
        updateEntryType(isCreateType)
    }

    private fun updateEntryType(isCreateType: Boolean) {
        if (isCreateType) {
            _entryType.value = AddTodoActionType.CREATE
            _uiState.value = uiState.value?.copy(entryType = AddTodoActionType.CREATE)
        } else {
            _entryType.value = AddTodoActionType.UPDATE
            _uiState.value = uiState.value?.copy(entryType = AddTodoActionType.UPDATE)
        }
    }


    fun onClick(eventType: AddTodoEventType) {
        // event 타입에 따라 set result
        val id = _id
        _result.value = when (eventType) {
            AddTodoEventType.ClickAddButton -> {
                AddTodoEventResultType.AddResult(
                    TodoModel(
                        _title,
                        _description,
                    )
                )
            }

            AddTodoEventType.ClickDeleteButton -> {
                id ?: run {
                    Timber.e("Can't find entryEntity's id")
                    return
                }
                AddTodoEventResultType.DeleteResult(
                    TodoModel(
                        _title,
                        _description,
                        id = id
                    )
                )
            }

            AddTodoEventType.ClickUpdateButton -> {
                id ?: run {
                    Timber.e("Can't find entryEntity's id")
                    return
                }
                AddTodoEventResultType.UpdateResult(
                    TodoModel(
                        _title,
                        _description,
                        id = id
                    )
                )
            }
        }
    }


    fun updateUiState(title: String, description: String) {
        Timber.d("updateUiState called")
        val isEnabled = title.isNotBlank() && description.isNotBlank()
        _uiState.value = _uiState.value?.copy(
            title = title,
            description = description,
            isSubmitButtonEnabled = isEnabled
        )
    }

}