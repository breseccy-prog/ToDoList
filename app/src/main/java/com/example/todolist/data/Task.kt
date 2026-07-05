package com.example.todolist.data

data class Task(
    val id: Long = 0,
    val title: String,
    val description: String,
    val category: String,
    val priority: String,
    val deadline: String,
    val isDone: Boolean = false
)
