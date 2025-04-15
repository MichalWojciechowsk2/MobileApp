package pl.wsei.pam.lab06.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoTaskDao {

    @Insert
    suspend fun insert(task: TodoTaskEntity): Long

    @Insert
    suspend fun insertAll(vararg tasks: TodoTaskEntity)

    @Delete
    suspend fun removeById(item: TodoTaskEntity)

    @Update
    suspend fun update(task: TodoTaskEntity)

    @Query("SELECT * FROM tasks ORDER BY deadline DESC")
    fun findAll(): Flow<List<TodoTaskEntity>>

    @Query("SELECT * FROM tasks WHERE id == :id")
    fun find(id: Int): Flow<TodoTaskEntity>
}


//PRAWIE DOBRZE, ZASTANOWIC SIE NAD TYM INSERTEM