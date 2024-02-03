package com.brandon.todo_app.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TodoEntity(
    val id: String?,
    val title: String? = null,
    val content: String? = null,
) : Parcelable
