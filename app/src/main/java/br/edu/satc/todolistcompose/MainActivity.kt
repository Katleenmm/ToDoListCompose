package br.edu.satc.todolistcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.lifecycleScope
import br.edu.satc.todolistcompose.data.TaskData
import br.edu.satc.todolistcompose.database.TaskDataBase
import br.edu.satc.todolistcompose.entidade.Task
import br.edu.satc.todolistcompose.ui.screens.HomeScreen
import br.edu.satc.todolistcompose.ui.theme.ToDoListComposeTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    private val tasksList = mutableStateListOf<TaskData>() // Compose-friendly

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = TaskDataBase.getDatabase(this)

        // Carrega tasks do banco
        lifecycleScope.launch(Dispatchers.IO) {
            val tasksFromDb = db.taskDao().getAll()
            tasksList.clear()
            tasksList.addAll(
                tasksFromDb.map { task ->
                    TaskData(
                        title = task.title ?: "",
                        description = task.description ?: "",
                        complete = task.complete ?: false
                    )
                }
            )
        }

        setContent {
            ToDoListComposeTheme {
                HomeScreen(
                    tasks = tasksList,
                    onAddTask = { title, description ->
                        val newTask = Task(
                            id_task = 0,
                            title = title,
                            description = description,
                            complete = false
                        )

                        // Insere no banco e atualiza a lista
                        lifecycleScope.launch(Dispatchers.IO) {
                            db.taskDao().insertAll(newTask)
                            val updatedTasks = db.taskDao().getAll().map {
                                TaskData(
                                    title = it.title ?: "",
                                    description = it.description ?: "",
                                    complete = it.complete ?: false
                                )
                            }
                            // Atualiza a lista no Main
                            withContext(Dispatchers.Main) {
                                tasksList.clear()
                                tasksList.addAll(updatedTasks)
                            }
                        }
                    }
                )
            }
        }
    }
}
