package com.brandon.todo_app.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class TodoModel(
    var title: String? = null,
    var description: String? = null,
    var isBookmarked: Boolean = false,
    val id: String = generateUniqueId(),
) : Parcelable

fun generateUniqueId(): String {
    return UUID.randomUUID().toString()
}