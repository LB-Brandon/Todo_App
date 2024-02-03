package com.brandon.todo_app.ui.todo.list

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.brandon.todo_app.ui.main.TodoMainViewModel


class TodoListFragment : Fragment() {

    private val viewModel: TodoListViewModel by viewModels()
    private val sharedViewModel: TodoMainViewModel by activityViewModels()


    companion object {
        fun newInstance() = TodoListFragment()
    }

}