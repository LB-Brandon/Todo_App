package com.brandon.todo_app.ui.todo.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.brandon.todo_app.data.TodoEntity
import com.brandon.todo_app.data.TodoListItem
import com.brandon.todo_app.util.SingleLiveEvent

class TodoListViewModel : ViewModel() {

    private val _event: SingleLiveEvent<TodoListEvent> = SingleLiveEvent()
    val event: LiveData<TodoListEvent> get() = _event


    // 리스트 어댑터로 부터 받은 데이터를 사용하여 Event 를 발생
    // 이후 TodoListFragment 에서 데이터를 TodoContentActivity 에 전달하며 이벤트 소모
    // TodoContentActivity 는 ListItem 이 아닌 Entity 를 받으므로 Entity 변환 후 전달
    // TODO: ResultLauncher 로 결과를 받으면 title, content 만 수정하여 업데이트
    fun onClickItem(position: Int, item: TodoListItem) {
        _event.value = when (item) {
            is TodoListItem.Item -> TodoListEvent.OpenContent(
                position,
                TodoEntity(
                    id = item.id,
                    title = item.title,
                    content = item.content
                )
            )
        }
    }


}
