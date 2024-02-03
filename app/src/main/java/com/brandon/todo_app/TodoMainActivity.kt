package com.brandon.todo_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.brandon.todo_app.databinding.TodoMainActivityBinding

class TodoMainActivity : AppCompatActivity() {

    private val binding: TodoMainActivityBinding by lazy {
        TodoMainActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.todo_main_activity)
    }
}