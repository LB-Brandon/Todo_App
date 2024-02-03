package com.brandon.todo_app.ui.todo.list

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.IntentCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.brandon.todo_app.data.TodoEntity
import com.brandon.todo_app.databinding.TodoListFragmentBinding
import com.brandon.todo_app.ui.main.TodoMainViewModel
import com.brandon.todo_app.ui.todo.content.TodoContentActionType
import com.brandon.todo_app.ui.todo.content.TodoContentConstant.EXTRA_TODO_CONTENT_ENTRY_TYPE
import com.brandon.todo_app.ui.todo.content.TodoContentConstant.EXTRA_TODO_ENTITY
import timber.log.Timber


class TodoListFragment : Fragment() {

    companion object {
        fun newInstance() = TodoListFragment()
    }

    private val viewModel: TodoListViewModel by viewModels()
    private val sharedViewModel: TodoMainViewModel by activityViewModels()

    private var _binding: TodoListFragmentBinding? = null
    private val binding get() = _binding!!

    private val listAdapter: TodoListAdapter by lazy {
        TodoListAdapter(
            onClickItem = { position, item ->
                viewModel.onClickItem(
                    position,
                    item
                )
            },
            onBookmarkChecked = { position, item ->

            }
        )
    }

    private val updateTodoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                val entryType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getSerializableExtra(
                        EXTRA_TODO_CONTENT_ENTRY_TYPE,
                        TodoContentActionType::class.java
                    )
                } else {
                    result.data?.getSerializableExtra(EXTRA_TODO_CONTENT_ENTRY_TYPE) as TodoContentActionType
                }

                val entity = IntentCompat.getParcelableExtra(
                    result.data ?: Intent(),
                    EXTRA_TODO_ENTITY,
                    TodoEntity::class.java
                )

                // TODO: 공유 뷰모델 데이터 업데이트 or 삭제
                updateSharedTodoList(entryType, entity)
            }
        }

    private fun updateSharedTodoList(entryType: TodoContentActionType?, entity: TodoEntity?) {
        entity ?: Timber.e("Can't find updated entity")
        entity ?: Timber.e("Can't find entry type")
        when (entryType) {
            TodoContentActionType.UPDATE -> TODO()
            TodoContentActionType.DELETE -> TODO()
            null -> Unit
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TodoListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initViewModel() {
        sharedViewModel.todoItemList.observe(viewLifecycleOwner) {
            listAdapter.submitList(it)
        }
    }

    private fun initView() = with(binding) {
        list.adapter = listAdapter
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}