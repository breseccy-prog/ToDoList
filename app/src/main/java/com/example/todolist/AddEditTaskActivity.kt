package com.example.todolist

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.data.Task
import com.example.todolist.data.TaskDatabaseHelper

class AddEditTaskActivity : AppCompatActivity() {

    private lateinit var dbHelper: TaskDatabaseHelper
    private lateinit var etTitle: EditText
    private lateinit var etDescription: EditText
    private lateinit var etCategory: EditText
    private lateinit var etDeadline: EditText
    private lateinit var spPriority: Spinner
    private lateinit var cbDone: CheckBox

    private var taskId: Long = -1
    private val priorities = listOf("Rendah", "Sedang", "Tinggi")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_task)

        dbHelper = TaskDatabaseHelper(this)
        taskId = intent.getLongExtra(EXTRA_TASK_ID, -1)

        setupView()
        setupSpinner()
        setupEditMode()
    }

    private fun setupView() {
        etTitle = findViewById(R.id.etTitle)
        etDescription = findViewById(R.id.etDescription)
        etCategory = findViewById(R.id.etCategory)
        etDeadline = findViewById(R.id.etDeadline)
        spPriority = findViewById(R.id.spPriority)
        cbDone = findViewById(R.id.cbDone)

        findViewById<Button>(R.id.btnSave).setOnClickListener { saveTask() }
        findViewById<Button>(R.id.btnBack).setOnClickListener { finish() }
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, priorities)
        spPriority.adapter = adapter
    }

    private fun setupEditMode() {
        if (taskId != -1L) {
            title = "Edit Tugas"
            val task = dbHelper.getTaskById(taskId)
            if (task != null) {
                etTitle.setText(task.title)
                etDescription.setText(task.description)
                etCategory.setText(task.category)
                etDeadline.setText(task.deadline)
                cbDone.isChecked = task.isDone
                spPriority.setSelection(priorities.indexOf(task.priority).coerceAtLeast(0))
            }
        } else {
            title = "Tambah Tugas"
        }
    }

    private fun saveTask() {
        val title = etTitle.text.toString().trim()
        val description = etDescription.text.toString().trim()
        val category = etCategory.text.toString().trim().ifBlank { "Umum" }
        val deadline = etDeadline.text.toString().trim().ifBlank { "-" }
        val priority = spPriority.selectedItem.toString()
        val isDone = cbDone.isChecked

        if (title.isBlank()) {
            etTitle.error = "Judul tugas wajib diisi"
            etTitle.requestFocus()
            return
        }

        val task = Task(
            id = if (taskId == -1L) 0 else taskId,
            title = title,
            description = description,
            category = category,
            priority = priority,
            deadline = deadline,
            isDone = isDone
        )

        if (taskId == -1L) {
            dbHelper.insertTask(task)
            Toast.makeText(this, "Tugas berhasil ditambahkan", Toast.LENGTH_SHORT).show()
        } else {
            dbHelper.updateTask(task)
            Toast.makeText(this, "Tugas berhasil diperbarui", Toast.LENGTH_SHORT).show()
        }
        finish()
    }

    companion object {
        const val EXTRA_TASK_ID = "extra_task_id"
    }
}
