package com.example.todoapp.UI

import com.example.todoapp.UI.fragments.AddTaskFragment
import com.example.todoapp.UI.fragments.SettingsFragment
import com.example.todoapp.UI.fragments.TasksFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todoapp.OnTaskAddedListener
import com.example.todoapp.R
import com.example.todoapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    lateinit var tasksFragment: TasksFragment
    // Validation when typing in compile time

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.fabAddTask.setOnClickListener {
            val addTaskBottomSheet = AddTaskFragment()
            addTaskBottomSheet.onTaskAddedListener = object : OnTaskAddedListener {
                override fun onTaskAdded() {
                    // Reload Fragment ->  TaskListFragment
                    tasksFragment.getTasks()
                }
            }
            addTaskBottomSheet.show(supportFragmentManager, null)
        }
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.tasks -> {
                    tasksFragment = TasksFragment()
                    pushFragment(tasksFragment)
                }

                R.id.settings -> {
                    pushFragment(SettingsFragment())
                }
            }
            return@setOnItemSelectedListener true
        }
        binding.bottomNavigation.selectedItemId = R.id.tasks

    }

    private fun pushFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.content.fragmentContainer.id, fragment)
            .commit()
    }
}