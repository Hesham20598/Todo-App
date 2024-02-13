package com.example.todoapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.database.models.Task
import java.util.Date

@Dao
interface TasksDao {

    @Insert
    fun insertTask(task: Task)
    @Update
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Query("select * from Task")
    fun getAllTasks(): List<Task>

    @Query("select * from Task where date = :date")
    fun getTasksByDate(date: Date): List<Task>
}