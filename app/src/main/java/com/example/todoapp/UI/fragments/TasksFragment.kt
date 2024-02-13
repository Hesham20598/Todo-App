package com.example.todoapp.UI.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.Keys
import com.example.todoapp.SwipeGesture
import com.example.todoapp.UI.TaskDetailsActivity
import com.example.todoapp.adapter.TasksAdapter
import com.example.todoapp.clearTime
import com.example.todoapp.database.TasksDatabase
import com.example.todoapp.databinding.FragmentTasksBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.util.Calendar
import java.util.Date

//import com.example.todoapp.databinding.FragmentTasksBinding

@Suppress("DEPRECATION")
class TasksFragment : Fragment() {
    private lateinit var binding: FragmentTasksBinding
    private lateinit var adapter: TasksAdapter
    private lateinit var calendar: Calendar
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.rvTasks
        adapter = TasksAdapter(null)
        calendar = Calendar.getInstance()
        // Practice - Assignment -> Todo
        binding.rvTasks.adapter = adapter
        val list = TasksDatabase.getInstance(requireContext()).getTasksDao().getAllTasks()

        adapter.onTaskItemClickListener = object: TasksAdapter.OnTaskItemClidkListener{
            override fun onTaskItemClick(position: Int) {
                val intent = Intent(requireContext(),TaskDetailsActivity::class.java)
                val task = list.get(position)
                intent.putExtra(Keys.SEND_TASK,task)
                startActivity(intent)
            }

        }

        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            val year = date.year
            val month = date.month - 1

            val dayOfMonth = date.day
            calendar.clearTime()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            getTasks()
        }

        adapter.updateData(list)

        val swipeGesture = object : SwipeGesture(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                adapter.deleteItem(position, requireContext())
            }

        }

        val itemTouchHelper = ItemTouchHelper(swipeGesture)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }


    fun getTasks() {
        val updatedList = TasksDatabase
            .getInstance(requireContext())
            .getTasksDao()
            .getTasksByDate(calendar.time)
        adapter.updateData(updatedList)
    }

}




