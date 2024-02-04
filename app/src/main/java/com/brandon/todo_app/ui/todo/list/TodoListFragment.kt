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
import com.brandon.todo_app.ui.todo.content.TodoContentActivity
import com.brandon.todo_app.ui.todo.content.TodoContentConstant.EXTRA_TODO_CONTENT_ACTION_TYPE
import com.brandon.todo_app.ui.todo.content.TodoContentConstant.EXTRA_TODO_ENTITY


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
            // 리스트의 아이템이 클릭되면 아이템의 position 과 item 자체를 받아와 viewModel로 전달
            onClickItem = { position, item ->
                viewModel.onClickItem(
                    position,
                    item
                )
            },
            onBookmarkChecked = { item ->
                sharedViewModel.toggleBookmark(item)
            }
        )
    }

    /**
     * TodoListFragment -> TodoContentActivity
     *
     * 리스트 아이템 클릭 후 돌려 받은 결과 처리 로직
     */
    private val updateTodoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                val entryType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getSerializableExtra(
                        EXTRA_TODO_CONTENT_ACTION_TYPE,
                        TodoContentActionType::class.java
                    )
                } else {
                    result.data?.getSerializableExtra(EXTRA_TODO_CONTENT_ACTION_TYPE) as TodoContentActionType
                }

                val entity = IntentCompat.getParcelableExtra(
                    result.data ?: Intent(),
                    EXTRA_TODO_ENTITY,
                    TodoEntity::class.java
                )

                updateSharedTodoList(entryType, entity)
            }
        }

    private fun updateSharedTodoList(entryType: TodoContentActionType?, entity: TodoEntity?) {
        sharedViewModel.updateTodoItem(entryType, entity)
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
        with(sharedViewModel){
            todoItemList.observe(viewLifecycleOwner) {
                listAdapter.submitList(it)
            }
        }
        with(viewModel){
            event.observe(viewLifecycleOwner){ event ->
                when(event){
                    is TodoListEvent.OpenContent -> {
                        updateTodoLauncher.launch(
                            TodoContentActivity.newIntentUpdate(
                                requireContext(),
                                event.position,
                                event.item
                            )
                        )
                    }
                }
            }
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