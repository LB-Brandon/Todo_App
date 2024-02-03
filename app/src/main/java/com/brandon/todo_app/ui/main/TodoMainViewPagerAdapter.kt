package com.jess.nbcamp.challnge2.assignment.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.brandon.todo_app.R
import com.brandon.todo_app.ui.bookmark.BookmarkListFragment
import com.brandon.todo_app.ui.todo.list.TodoListFragment

class TodoMainViewPagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    private val fragments = listOf(
        TodoMainTab(TodoListFragment.newInstance(), R.string.main_tab_todo_title),
        TodoMainTab(BookmarkListFragment.newInstance(), R.string.main_tab_bookmark_title),
    )

    fun getFragment(position: Int): Fragment = fragments[position].fragment

    fun getTodoListFragment(): Fragment? = fragments.find {
        it.fragment::class.java == TodoListFragment::class.java
    }?.fragment

//    fun getFragment(clazz: Class<out Fragment>): Fragment? =
//        fragments.find { it.fragment::class.java == clazz }?.fragment

    fun getTitle(position: Int): Int = fragments[position].title

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position].fragment

}