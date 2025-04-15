package pl.wsei.pam.lab06

import java.time.LocalDate

//fun todoTasks(): List<TodoTask> {
//    return listOf(
//        TodoTask(1,"Programming", LocalDate.of(2024, 4, 18), false, Priority.Low),
//        TodoTask(2,"Teaching", LocalDate.of(2024, 5, 12), false, Priority.High),
//        TodoTask(3,"Learning", LocalDate.of(2024, 6, 28), true, Priority.Low),
//        TodoTask(4,"Cooking", LocalDate.of(2024, 8, 18), false, Priority.Medium),
//    )
//}

enum class Priority() {
   High, Medium, Low
}

data class TodoTask(
   val id: Int=0,
   val title: String,
   val deadline: LocalDate,
   val isDone: Boolean,
   val priority: Priority
)