package com.brandon.todo_app.ui.todo_add_todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class AddTodoViewModel : ViewModel() {

    private val _event: MutableLiveData<AddTodoEventType> = MutableLiveData()
    val event: LiveData<AddTodoEventType> = _event

    private val _uiState: MutableLiveData<AddTodoUiState> = MutableLiveData(AddTodoUiState.init())
    val uiState: LiveData<AddTodoUiState> get() = _uiState

    fun onClick(eventType: AddTodoEventType) {
        _event.value = eventType
    }

    fun updateUiState(title: String, description: String, entryType: AddTodoEntryType) {
        Timber.d("updateUiState called")
        _uiState.value = _uiState.value?.copy(title = title, description = description, entryType = entryType)
        updateButtonState()
    }

    private fun updateButtonState() {
        val isTitleNotBlank = _uiState.value?.title?.isNotBlank() == true
        val isDescriptionNotBlank = _uiState.value?.description?.isNotBlank() == true

        _uiState.value =
            _uiState.value?.copy(isSubmitButtonEnabled = isTitleNotBlank && isDescriptionNotBlank)
    }

}