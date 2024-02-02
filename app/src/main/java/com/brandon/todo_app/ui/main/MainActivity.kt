package com.brandon.todo_app.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.brandon.nbc_todo.ui.main.MainViewPagerAdapter
import com.brandon.todo_app.databinding.MainActivityBinding
import com.brandon.todo_app.ui.todo_list.TodoListFragment
import com.google.android.material.tabs.TabLayoutMediator
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val binding: MainActivityBinding by lazy { MainActivityBinding.inflate(layoutInflater) }
    private val viewPagerAdapter: MainViewPagerAdapter by lazy { MainViewPagerAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        initView()
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
}