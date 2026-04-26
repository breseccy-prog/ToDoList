package com.example.todolist

object TaskRepository {

    private val taskList = mutableListOf<Task>()
    private var nextId = 1

    fun getAllTasks(): MutableList<Task> {
        return taskList
    }

    fun addTask(title: String, subject: String, deadline: String, description: String) {
        val task = Task(
            id = nextId,
            title = title,
            subject = subject,
            deadline = deadline,
            description = description
        )
        taskList.add(task)
        nextId++
    }

    fun getTaskById(id: Int): Task? {
        return taskList.find { it.id == id }
    }

    fun deleteTask(id: Int) {
        taskList.removeAll { it.id == id }
    }
}