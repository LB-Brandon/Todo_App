package com.brandon.todo_app.todo.content

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.brandon.todo_app.data.TodoEntity
import com.brandon.todo_app.databinding.TodoCreateActivityBinding
import com.brandon.todo_app.todo.content.TodoContentConstant.EXTRA_TODO_ENTITY
import com.brandon.todo_app.todo.content.TodoContentConstant.EXTRA_TODO_ENTRY_TYPE
import com.brandon.todo_app.todo.content.TodoContentConstant.EXTRA_TODO_POSITION

class TodoContentActivity : AppCompatActivity() {

    companion object {

        fun newIntentCreate(
            context: Context
        ) = Intent(context, TodoContentActivity::class.java).apply {
            putExtra(EXTRA_TODO_ENTRY_TYPE, TodoContentEntryType.CREATE)
        }

        fun newIntentUpdate(
            context: Context,
            position: Int,
            entity: TodoEntity
        ) = Intent(context, TodoContentActivity::class.java).apply {
            putExtra(EXTRA_TODO_ENTRY_TYPE, TodoContentEntryType.UPDATE)
            putExtra(EXTRA_TODO_POSITION, position)
            putExtra(EXTRA_TODO_ENTITY, entity)
        }
    }

    private val binding: TodoCreateActivityBinding by lazy {
        TodoCreateActivityBinding.inflate(layoutInflater)
    }


}