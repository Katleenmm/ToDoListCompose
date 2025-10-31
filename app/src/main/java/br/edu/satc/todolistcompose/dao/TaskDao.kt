package br.edu.satc.todolistcompose.dao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.edu.satc.todolistcompose.entidade.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks WHERE id_task= :id_task")
    fun getTask(id_task: String): Task
    @Query("SELECT * FROM tasks")
    fun getAll(): List<Task>
    @Insert
    fun insertAll(vararg tasks: Task)

    @Query("UPDATE tasks SET complete = :isComplete WHERE id_task = :id")
    suspend fun updateComplete(id: Int, isComplete: Boolean)

}




