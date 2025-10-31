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

    @Query("SELECT * FROM tasks WHERE title LIKE :title")
    fun loadAllByName(title: String): List<Task>

    @Insert
    fun insertAll(vararg tasks: Task)

    @Delete
    fun delete(tasks: Task)
}




