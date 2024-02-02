package com.brandon.todo_app.ui.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.brandon.nbc_todo.ui.main.MainViewPagerAdapter
import com.brandon.todo_app.const.Keys.EXTRA_TODO_MODEL
import com.brandon.todo_app.data.TodoModel
import com.brandon.todo_app.databinding.MainActivityBinding
import com.brandon.todo_app.ui.todo_add_todo.AddTodoActivity
import com.brandon.todo_app.ui.todo_list.TodoListFragment
import com.google.android.material.tabs.TabLayoutMediator
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val binding: MainActivityBinding by lazy { MainActivityBinding.inflate(layoutInflater) }
    private val viewPagerAdapter: MainViewPagerAdapter by lazy { MainViewPagerAdapter(this) }

    private val mainViewModel: MainSharedViewModel by viewModels()

    private val addTodoLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            handleAddTodoResult(it)
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        initView()
        initListeners()
    }

    private fun initView() = with(binding) {
        vpMain.adapter = viewPagerAdapter
        vpMain.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (viewPagerAdapter.getFragment(position) is TodoListFragment) {
                    fabCreateTodo.show()
                } else {
                    fabCreateTodo.hide()
                }
            }
        })
        TabLayoutMediator(tlMain, vpMain) { tab, position ->
            tab.setText(viewPagerAdapter.getTitle(position))
        }.attach()
    }

    private fun initListeners() = with(binding) {
        fabCreateTodo.setOnClickListener {
            addTodoLauncher.launch(
                AddTodoActivity.newIntent(this@MainActivity)
            )
        }
    }

    private fun handleAddTodoResult(result: ActivityResult) {
        if (result.resultCode == RESULT_OK) {
            val resultData = result.data
            if (resultData != null) {
                // AddTodoActivity에서 TodoModel 가져오기
                val newTodoModel = getTodoModelFromAddTodoLauncher(resultData)
                newTodoModel?.let {
                    // TodoFragment에 데이터를 전달
                    mainViewModel.addTodoItem(newTodoModel)
                    Timber.d("Received TodoModel from AddTodoActivity: $newTodoModel")
                } ?: run {
                    Timber.e("Received null TodoModel from AddTodoActivity.")
                }
            } else {
                Timber.e("No data received from AddTodoActivity.")
            }
            Timber.d("AddTodoActivity succeeded.")
        } else {
            Timber.e("AddTodoActivity failed or canceled.")
        }
    }

    private fun getTodoModelFromAddTodoLauncher(resultData: Intent): TodoModel? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            resultData.getParcelableExtra(EXTRA_TODO_MODEL, TodoModel::class.java)
        } else {
            resultData.getParcelableExtra(EXTRA_TODO_MODEL)
        }
    }
}