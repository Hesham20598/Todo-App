package com.example.todoapp.UI

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.Keys
import com.example.todoapp.clearTime
import com.example.todoapp.database.TasksDatabase
import com.example.todoapp.database.models.Task
import com.example.todoapp.databinding.ActivityTaskDetailsBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TaskDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskDetailsBinding
    private lateinit var calendar: Calendar

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        calendar = Calendar.getInstance()
        binding = ActivityTaskDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val task = intent.parcelable<Task>(Keys.SEND_TASK)
        bindData(task)
        Log.e("taskShow", "onCreate: ${task}")

        binding.content.selectDateTv.setOnClickListener {
            val picker = DatePickerDialog(
                this,
                object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(
                        view: DatePicker?, year: Int, month: Int, dayOfMonth: Int
                    ) {
                        calendar.set(Calendar.YEAR, year)
                        calendar.set(Calendar.MONTH, month)
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        binding.content.selectDateTv.text = "$dayOfMonth / ${month + 1} / $year"
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),


                )
            picker.datePicker.minDate = System.currentTimeMillis()
            picker.show()
        }
        validateFeilds()
        binding.content.btnSave.setOnClickListener {

            if (validateFeilds()) {
                calendar.clearTime()
                val newTask = Task(
                    title = binding.content.title.text.toString(),
                    description = binding.content.description.text.toString(),
                    isDone = false,
                    date = calendar.time
                )
                TasksDatabase.getInstance(this).getTasksDao().deleteTask(task!!)
                TasksDatabase.getInstance(this).getTasksDao().insertTask(newTask)
                finish()
            }
        }

    }

    private fun validateFeilds(): Boolean {
        if (binding.content.title.text?.isEmpty() == true || binding.content.title.text?.isBlank() == true) {
            binding.content.title.error = "Required"
            return false
        } else {
            binding.content.selectDateTil.error = null
            if (binding.content.description.text?.isEmpty() == true || binding.content.description.text?.isBlank() == true) {
                binding.content.description.error = "Required"
                return false
            } else {
                binding.content.selectDateTil.error = null
                return true
            }

        }
    }


    private fun bindData(task: Task?) {
        binding.content.title.setText(task?.title)
        binding.content.description.setText(task?.description)
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formatedDate = simpleDateFormat.format(task?.date!!)
        binding.content.selectDateTv.text = formatedDate
    }

}

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}