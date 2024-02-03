package com.brandon.todo_app.ui.todo_add_todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brandon.todo_app.data.TodoModel
import timber.log.Timber

class AddTodoViewModel : ViewModel() {

    private val _entryEntity: MutableLiveData<TodoModel?> = MutableLiveData()

    private var _entryType: MutableLiveData<AddTodoActionType> = MutableLiveData()
    val entryType: LiveData<AddTodoActionType> = _entryType

    private val _event: MutableLiveData<AddTodoEventType> = MutableLiveData()
    val event: LiveData<AddTodoEventType> = _event

    private val _result: MutableLiveData<AddTodoEventResultType> = MutableLiveData()
    val result: LiveData<AddTodoEventResultType> = _result

    private val _uiState: MutableLiveData<AddTodoUiState> = MutableLiveData(AddTodoUiState.init())
    val uiState: LiveData<AddTodoUiState> get() = _uiState


    // 라이브 데이터 값 노출
    private val _title: String? get() = _uiState.value?.title
    private val _description: String? get() = _uiState.value?.description
    private val _id: String? get() = _entryEntity.value?.id


    fun setEntryEntity(entryEntity: TodoModel?) {
        entryEntity ?: run {
            Timber.d("setEntryEntity is null")
            return
        }
        Timber.d("setEntryEntity is $entryEntity")

        _entryEntity.value = entryEntity
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