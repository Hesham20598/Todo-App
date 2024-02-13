package com.example.todoapp.database

import androidx.room.TypeConverter
import java.util.Date

class DateTypeConverter {
    @TypeConverter
    fun convertToDate(dateTime: Long): Date {

        return Date(dateTime)
    }

    @TypeConverter
    fun convertFromDate(date: Date): Long {
        return date.time
    }
}