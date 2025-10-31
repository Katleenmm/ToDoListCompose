package br.edu.satc.todolistcompose.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.edu.satc.todolistcompose.dao.TaskDao
import br.edu.satc.todolistcompose.entidade.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDataBase : RoomDatabase(){
    abstract fun taskDao(): TaskDao
    companion object{
        @Volatile
        private var Instance: TaskDataBase? = null
        fun getDatabase(context: Context): TaskDataBase {

            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TaskDataBase::class.java, "task_database")
                    .build().also { Instance = it }
            }
        }
    }
}
