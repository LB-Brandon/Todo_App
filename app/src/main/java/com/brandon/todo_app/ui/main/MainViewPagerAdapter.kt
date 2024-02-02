package com.brandon.nbc_todo.ui.main

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.brandon.todo_app.R
import com.brandon.todo_app.ui.main.MainTabHolder
import com.brandon.todo_app.ui.todo_bookmark_list.BookmarkFragment
import com.brandon.todo_app.ui.todo_list.TodoListFragment


class MainViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private var fragments: List<MainTabHolder> = listOf(
        MainTabHolder(
            fragment = TodoListFragment.newInstance(), title = R.string.MainActivity_todo_tab_tittle
        ), MainTabHolder(
            fragment = BookmarkFragment.newInstance(), title = R.string.MainActivity_bookmark_tab_title
        )
    )

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position].fragment

    fun getFragment(position: Int) = fragments[position].fragment

    fun getTitle(position: Int) = fragments[position].title

    fun getTodoListFragment(): TodoListFragment? = fragments.find {
        it.fragment is TodoListFragment
    }?.fragment as TodoListFragment?
}