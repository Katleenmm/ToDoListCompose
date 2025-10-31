package br.edu.satc.todolistcompose.entidade

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "tasks")
class Task(
    @PrimaryKey val id_task: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "complete") val complete: Boolean?
)
