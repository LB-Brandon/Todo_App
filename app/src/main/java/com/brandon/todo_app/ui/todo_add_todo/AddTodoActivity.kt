package com.brandon.todo_app.ui.todo_add_todo

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.brandon.todo_app.const.Keys.EXTRA_ADD_TODO_POSITION
import com.brandon.todo_app.const.Keys.EXTRA_TODO_MODEL
import com.brandon.todo_app.data.TodoModel
import com.brandon.todo_app.databinding.AddTodoActivityBinding
import com.brandon.todo_app.ui.main.MainSharedViewModel
import timber.log.Timber

class AddTodoActivity : AppCompatActivity() {

    private val binding: AddTodoActivityBinding by lazy { AddTodoActivityBinding.inflate(layoutInflater) }
    private val viewModel: AddTodoViewModel by viewModels()
    private val mainSharedViewModel: MainSharedViewModel by viewModels()


    private val todoEntity: TodoModel? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(EXTRA_TODO_MODEL, TodoModel::class.java)
        } else {
            intent?.getParcelableExtra(EXTRA_TODO_MODEL)
        }
    }
    private lateinit var entryType: AddTodoEntryType

    private val editTexts get() = listOf(binding.etTitle, binding.etDescription)

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, AddTodoActivity()::class.java)

        fun newIntent(
            context: Context, position: Int, entity: TodoModel
        ) = Intent(context, AddTodoActivity::class.java).apply {
            putExtra(EXTRA_ADD_TODO_POSITION, position)
            putExtra(EXTRA_TODO_MODEL, entity)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setEntryType()
        initListener()
        initViewModel()
    }

    private fun initViewModel() = with(viewModel) {
        uiState.observe(this@AddTodoActivity){
            // Button 활/비활성화 상태 토글
            when(entryType){
                AddTodoEntryType.CREATE -> binding.btnAdd.isEnabled = it.isSubmitButtonEnabled == true
                AddTodoEntryType.UPDATE -> binding.btnEdit.isEnabled = it.isSubmitButtonEnabled == true
            }
            Timber.d("Submit button state changed")
        }
        event.observe(this@AddTodoActivity){
            when(it){
                AddTodoEventType.ClickAddButton -> {
                    Timber.d("Add Button Clicked")
                }
                AddTodoEventType.ClickDeleteButton -> {
                    Timber.d("Delete Button Clicked")
                }
                AddTodoEventType.ClickUpdateButton -> {
                    Timber.d("Update Button Clicked")
                }
            }
        }
    }

    // Entity 존재 여부로 entryType 분기, 초기갑 세팅
    private fun setEntryType() = with(binding) {
        entryType = if (todoEntity == null) {
            Timber.d("Fetch failed, Create Action")
            AddTodoEntryType.CREATE
        } else {
            Timber.d("Fetch success, Update Action")
            etTitle.setText(todoEntity?.title)
            etDescription.setText(todoEntity?.description)
            AddTodoEntryType.UPDATE
        }
        // Update entry type debug
        entryType = AddTodoEntryType.UPDATE
        when (entryType) {
            AddTodoEntryType.UPDATE -> {
                btnEdit.isVisible = true
                btnDelete.isVisible = true
                btnAdd.isVisible = false
            }

            else -> Unit
        }
    }

    private fun initListener() = with(binding) {
        // Submit 버튼 활/비활성화
        setTextChangeListenersForButtons()

        // 버튼 클릭 이벤트 발생
        btnAdd.setOnClickListener {
            viewModel.onClick(AddTodoEventType.ClickAddButton)
        }
        btnEdit.setOnClickListener {
            viewModel.onClick(AddTodoEventType.ClickUpdateButton)
        }
        btnDelete.setOnClickListener {
            viewModel.onClick(AddTodoEventType.ClickDeleteButton)
        }
    }


    private fun setTextChangeListenersForButtons() {
        editTexts.forEach { editText ->
            editText.addTextChangedListener {
                it?.let {
                    val title = binding.etTitle.text.toString()
                    val description = binding.etDescription.text.toString()
                    viewModel.updateUiState(title, description, entryType)
                }
            }
        }
    }
}


