package com.example.todoapp.database.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date
@Parcelize
@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int?=null,
    var title: String? = null,
    var description: String? = null,
    var date: Date? = null,
    var isDone: Boolean? = false
):Parcelable
