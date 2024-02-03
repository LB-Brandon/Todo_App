package com.brandon.todo_app.ui.main

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.brandon.todo_app.data.TodoEntity
import com.brandon.todo_app.databinding.TodoMainActivityBinding
import com.brandon.todo_app.ui.todo.content.TodoContentActivity
import com.brandon.todo_app.ui.todo.content.TodoContentConstant.EXTRA_TODO_ENTITY
import com.brandon.todo_app.ui.todo.list.TodoListFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.jess.nbcamp.challnge2.assignment.main.TodoMainViewPagerAdapter
import timber.log.Timber

class TodoMainActivity : AppCompatActivity() {

    private val binding: TodoMainActivityBinding by lazy {
        TodoMainActivityBinding.inflate(layoutInflater)
    }
    private val viewPagerAdapter by lazy {
        TodoMainViewPagerAdapter(this)
    }

    private val sharedViewModel: TodoMainViewModel by viewModels()

    private val createTodoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val todoModel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra(
                        EXTRA_TODO_ENTITY,
                        TodoEntity::class.java
                    )
                } else {
                    result.data?.getParcelableExtra(
                        EXTRA_TODO_ENTITY
                    )
                }
                // TODO: 공유 뷰모델에 저장
                sharedViewModel.saveTodoModel(todoModel)
                Timber.d("AddTodoActivity succeeded.")
            }else{
                Timber.e("AddTodoActivity failed or canceled.")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Timber.plant(Timber.DebugTree())


        initView()

    }

    private fun initView() = with(binding) {
        viewPager.adapter = viewPagerAdapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (viewPagerAdapter.getFragment(position) is TodoListFragment) {
                    fabCreateTodo.show()
                } else {
                    fabCreateTodo.hide()
                }
            }
        })

        // TabLayout x ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setText(viewPagerAdapter.getTitle(position))
        }.attach()

        // fab
        fabCreateTodo.setOnClickListener {
            createTodoLauncher.launch(
                TodoContentActivity.newIntentCreate(this@TodoMainActivity)
            )
        }
    }
}