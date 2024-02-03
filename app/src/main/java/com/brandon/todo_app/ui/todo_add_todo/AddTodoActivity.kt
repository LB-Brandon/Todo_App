package com.brandon.todo_app.ui.todo_add_todo

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.brandon.todo_app.const.Keys.EXTRA_ADD_TODO_EVENT_TYPE
import com.brandon.todo_app.const.Keys.EXTRA_ADD_TODO_POSITION
import com.brandon.todo_app.const.Keys.EXTRA_TODO_ENTITY
import com.brandon.todo_app.data.TodoModel
import com.brandon.todo_app.databinding.AddTodoActivityBinding
import com.brandon.todo_app.ui.todo_add_todo.AddTodoActionType.CREATE
import com.brandon.todo_app.ui.todo_add_todo.AddTodoActionType.DELETE
import com.brandon.todo_app.ui.todo_add_todo.AddTodoActionType.UPDATE
import timber.log.Timber

class AddTodoActivity : AppCompatActivity() {

    private val binding: AddTodoActivityBinding by lazy { AddTodoActivityBinding.inflate(layoutInflater) }
    private val viewModel: AddTodoViewModel by viewModels()


    private val entryEntity: TodoModel? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(EXTRA_TODO_ENTITY, TodoModel::class.java)
        } else {
            intent?.getParcelableExtra(EXTRA_TODO_ENTITY)
        }
    }
    private val editTexts get() = listOf(binding.etTitle, binding.etDescription)

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, AddTodoActivity()::class.java)

        fun newIntent(
            context: Context, position: Int, entity: TodoModel
        ) = Intent(context, AddTodoActivity::class.java).apply {
            putExtra(EXTRA_ADD_TODO_POSITION, position)
            putExtra(EXTRA_TODO_ENTITY, entity)
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
        // EntryEntity 에 따른 초기 Ui 데이터 세팅
        entryEntity.observe(this@AddTodoActivity) { todoModel ->
            setData(todoModel)
        }
        // EntryType 에 따른 Ui 형태 초기화
        entryType.observe(this@AddTodoActivity) { entryType ->
            setInitUi(entryType)
        }
        // EntryType 업데이트 시 uiState 에 entryType 을 추가
        // type 에 적합한 ui만 업데이트
        uiState.observe(this@AddTodoActivity) { state ->
            updateSubmitButtonState(state.entryType, state.isSubmitButtonEnabled)
        }
        // 유저 이벤트가 발생하면 viewModel 내의 데이터를 가공하여 result 를 세팅하게 되고 result 를 관찰하여 결과 방출.
        result.observe(this@AddTodoActivity) { result ->
            sendResult(result)
        }

    }

    private fun setData(entryEntity: TodoModel?) {
        entryEntity ?: run {
            Timber.d("Can't find entry data")
            return
        }
        with(binding) {
            etTitle.setText(entryEntity.title.toString())
            etDescription.setText(entryEntity.description.toString())
        }
        Timber.d("Set entry data")
    }

    private fun sendResult(result: AddTodoEventResultType) {
        val intent = Intent()
        val resultTodoModel = when (result) {
            is AddTodoEventResultType.AddResult -> {
                intent.putExtra(EXTRA_ADD_TODO_EVENT_TYPE, CREATE.ordinal)
                result.todoModel
            }

            is AddTodoEventResultType.DeleteResult -> {
                intent.putExtra(EXTRA_ADD_TODO_EVENT_TYPE, DELETE.ordinal)
                result.todoModel
            }

            is AddTodoEventResultType.UpdateResult -> {
                intent.putExtra(EXTRA_ADD_TODO_EVENT_TYPE, UPDATE.ordinal)
                result.todoModel
            }
        }
        intent.putExtra(EXTRA_TODO_ENTITY, resultTodoModel)

        setResult(RESULT_OK, intent)
        Timber.d("Result Sent!")
        exit()
    }

    private fun setInitUi(entryType: AddTodoActionType?) = with(binding) {
        when (entryType) {
            UPDATE -> {
                btnEdit.isVisible = true
                btnDelete.isVisible = true
                btnAdd.isVisible = false
                Timber.d("Ui set, $entryType")
            }

            else -> Timber.d("Create Ui set")

        }
    }

    private fun updateSubmitButtonState(entryType: AddTodoActionType?, isEnabled: Boolean?) {
        Timber.d("Update button state, entryType: $entryType, isEnabled: $isEnabled.")
        when (entryType) {
            CREATE -> binding.btnAdd.isEnabled = isEnabled == true
            UPDATE -> binding.btnEdit.isEnabled = isEnabled == true
            else -> Unit
        }
        Timber.d("Submit button state changed")
    }

    // Entity 존재 여부로 entryType 분기, 초기갑 세팅
    private fun setEntryType() {
        viewModel.setEntryEntity(entryEntity)
    }

    private fun initListener() = with(binding) {
        // Submit 버튼 활/비활성화
        setTextChangeListenersForButtons()

        // 버튼 클릭 이벤트 발생
        btnAdd.setOnClickListener {
            Timber.d("Add Button Clicked")
            viewModel.onClick(AddTodoEventType.ClickAddButton)
        }
        btnEdit.setOnClickListener {
            Timber.d("Edit Button Clicked")
            viewModel.onClick(AddTodoEventType.ClickUpdateButton)
        }
        btnDelete.setOnClickListener {
            Timber.d("Delete Button Clicked")
            viewModel.onClick(AddTodoEventType.ClickDeleteButton)
        }
    }


    private fun setTextChangeListenersForButtons() {
        editTexts.forEach { editText ->
            editText.addTextChangedListener {
                it?.let {
                    val title = binding.etTitle.text.toString()
                    val description = binding.etDescription.text.toString()
                    viewModel.updateUiState(title, description)
                }
            }
        }
    }

    private fun exit() {
        if (!isFinishing) {
            Timber.e("Finish")
            finish()
        }
    }
}


