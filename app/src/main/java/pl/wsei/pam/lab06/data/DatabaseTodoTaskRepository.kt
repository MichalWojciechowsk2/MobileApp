package pl.wsei.pam.lab06.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.wsei.pam.lab06.data.database.TodoTaskDao
import pl.wsei.pam.lab06.data.database.TodoTaskEntity
import pl.wsei.pam.lab06.TodoTask

class DatabaseTodoTaskRepository(val dao: TodoTaskDao) : TodoTaskRepository {

    override fun getAllAsStream(): Flow<List<TodoTask>> {
        return dao.findAll().map { it ->
            it.map {
                it.toModel()
            }
        }
    }

    override fun getItemAsStream(id: Int): Flow<TodoTask?> {
        return dao.find(id).map {
            it.toModel()
        }
    }

    override suspend fun insertItem(item: TodoTask) {
//       val entity = TodoTaskEntity.fromModel(item)
//       val newId = dao.insert(entity)
        Log.d("Repository", "Inserting into DB: ${item.title}")
        dao.insertAll(TodoTaskEntity.fromModel(item))
    }

    override suspend fun deleteItem(item: TodoTask) {
        dao.removeById(TodoTaskEntity.fromModel(item))
    }

    override suspend fun updateItem(item: TodoTask) {
        dao.update(TodoTaskEntity.fromModel(item))
    }
}
