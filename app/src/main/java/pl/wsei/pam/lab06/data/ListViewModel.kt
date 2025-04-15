package pl.wsei.pam.lab06.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import pl.wsei.pam.lab06.TodoTask

class ListViewModel(val repository: TodoTaskRepository) : ViewModel() {
    init {
        Log.d("ListViewModel", "ListViewModel initialized")
    }

    val listUiState: StateFlow<ListUiState>
        get() {
            return repository.getAllAsStream().map { tasks ->
                ListUiState(tasks)
            }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                    initialValue = ListUiState()
                )
        }
    fun updateTask(task: TodoTask) {
        viewModelScope.launch {
            repository.updateItem(task)
        }
    }

    fun deleteTask(task: TodoTask) {
        viewModelScope.launch {
            repository.deleteItem(task)
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class ListUiState(val items: List<TodoTask> = listOf())
