package com.example.todoapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todoapp.database.TasksDatabase
import com.example.todoapp.database.models.Task
import com.example.todoapp.databinding.ItemTaskBinding
import java.text.SimpleDateFormat
import java.util.Locale


class TasksAdapter(
    private var tasksList: List<Task>?,
    var onTaskItemClickListener: OnTaskItemClidkListener? = null
) :
    Adapter<TasksAdapter.TasksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bindinig = ItemTaskBinding.inflate(inflater, parent, false)
        return TasksViewHolder(bindinig)
    }

    override fun getItemCount(): Int {
        return tasksList?.size ?: 0
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val item = tasksList?.get(position) ?: return
        holder.bindinig.root.setOnClickListener {
            onTaskItemClickListener?.onTaskItemClick(item)
        }
        holder.bind(item)
    }

    fun deleteItem(position: Int, context: Context) {
        val deletedItem = tasksList?.get(position)!!
        val database = TasksDatabase.getInstance(context)
        database.getTasksDao().deleteTask(deletedItem)
        notifyDataSetChanged()
        val newList = tasksList as ArrayList
        newList.removeAt(position)
        val updatedList = newList as List<Task>
        tasksList = updatedList
    }

    fun updateData(tasksList: List<Task>?) {
        this.tasksList = tasksList
        notifyDataSetChanged()
    }

    class TasksViewHolder(val bindinig: ItemTaskBinding) : ViewHolder(bindinig.root) {

        fun bind(task: Task) {
            bindinig.title.text = task.title
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formatedDate = simpleDateFormat.format(task.date!!)
            bindinig.time.text = formatedDate
        }

    }

    interface OnTaskItemClidkListener {
        fun onTaskItemClick(task:Task)
    }

}