package pl.wsei.pam.lab06.data

import android.content.Context
import pl.wsei.pam.lab06.NotificationHandler
import pl.wsei.pam.lab06.data.database.AppDatabase
import java.time.LocalDate

interface AppContainer {
    val todoTaskRepository: TodoTaskRepository
    val currentDateProvider: CurrentDateProvider
    val notificationHandler: NotificationHandler
}

class AppDataContainer(private val context: Context): AppContainer {
    override val todoTaskRepository: TodoTaskRepository by lazy {
        DatabaseTodoTaskRepository(AppDatabase.getInstance(context).taskDao())
    }
    override val notificationHandler: NotificationHandler by lazy {
        NotificationHandler(context)
    }
    override val currentDateProvider: CurrentDateProvider = DefaultCurrentDateProvider()
}