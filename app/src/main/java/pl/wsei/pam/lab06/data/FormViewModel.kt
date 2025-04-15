package pl.wsei.pam.lab06.data

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import pl.wsei.pam.lab06.TodoTask
import java.time.LocalDate

interface CurrentDateProvider {
    val currentDate: LocalDate
}

class DefaultCurrentDateProvider : CurrentDateProvider {
    override val currentDate: LocalDate
        get() = LocalDate.now()
}

class FormViewModel(
    private val repository: TodoTaskRepository,
    private val currentDateProvider: CurrentDateProvider = DefaultCurrentDateProvider()
) : ViewModel() {

    var todoTaskUiState by mutableStateOf(TodoTaskUiState())
        private set

    suspend fun save() {
        Log.d("FormViewModel", "save() called")
        if (validate()) {
            repository.insertItem(todoTaskUiState.todoTask.toTodoTask())
            Log.d("FormViewModel", "Inserting task: $todoTaskUiState")
        }
        else{
            Log.d("FormViewModel", "Validation failed")
        }
    }

    fun updateUiState(todoTaskForm: TodoTaskForm) {
        todoTaskUiState = TodoTaskUiState(
            todoTask = todoTaskForm,
            isValid = validate(todoTaskForm)
        )
    }

   private fun validate(uiState: TodoTaskForm = todoTaskUiState.todoTask): Boolean {
        val currentDate = currentDateProvider.currentDate
        val deadlineDate = LocalDate.ofEpochDay(uiState.deadline / (24 * 60 * 60 * 1000))
        return uiState.title.isNotBlank() && deadlineDate.isAfter(currentDate)
   }

}
