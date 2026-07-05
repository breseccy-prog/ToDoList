package com.example.todolist

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.data.Task
import com.example.todolist.data.TaskDatabaseHelper

class DetailTaskActivity : AppCompatActivity() {

    private lateinit var dbHelper: TaskDatabaseHelper
    private var taskId: Long = -1
    private var currentTask: Task? = null

    private lateinit var tvTitle: TextView
    private lateinit var tvDescription: TextView
    private lateinit var tvCategory: TextView
    private lateinit var tvPriority: TextView
    private lateinit var tvDeadline: TextView
    private lateinit var tvStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_task)

        dbHelper = TaskDatabaseHelper(this)
        taskId = intent.getLongExtra(EXTRA_TASK_ID, -1)

        setupView()
        loadTask()
    }

    override fun onResume() {
        super.onResume()
        loadTask()
    }

    private fun setupView() {
        tvTitle = findViewById(R.id.tvTitle)
        tvDescription = findViewById(R.id.tvDescription)
        tvCategory = findViewById(R.id.tvCategory)
        tvPriority = findViewById(R.id.tvPriority)
        tvDeadline = findViewById(R.id.tvDeadline)
        tvStatus = findViewById(R.id.tvStatus)

        findViewById<Button>(R.id.btnEdit).setOnClickListener {
            val intent = Intent(this, AddEditTaskActivity::class.java)
            intent.putExtra(AddEditTaskActivity.EXTRA_TASK_ID, taskId)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnDelete).setOnClickListener {
            showDeleteDialog()
        }

        findViewById<Button>(R.id.btnBack).setOnClickListener { finish() }
    }

    private fun loadTask() {
        currentTask = dbHelper.getTaskById(taskId)
        val task = currentTask
        if (task == null) {
            Toast.makeText(this, "Data tugas tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        tvTitle.text = task.title
        tvDescription.text = task.description.ifBlank { "Tidak ada deskripsi" }
        tvCategory.text = "Kategori: ${task.category}"
        tvPriority.text = "Prioritas: ${task.priority}"
        tvDeadline.text = "Deadline: ${task.deadline}"
        tvStatus.text = "Status: ${if (task.isDone) "Selesai" else "Belum selesai"}"
    }

    private fun showDeleteDialog() {
        AlertDialog.Builder(this)
            .setTitle("Hapus Tugas")
            .setMessage("Apakah kamu yakin ingin menghapus tugas ini?")
            .setPositiveButton("Hapus") { _, _ ->
                dbHelper.deleteTask(taskId)
                Toast.makeText(this, "Tugas berhasil dihapus", Toast.LENGTH_SHORT).show()
                finish()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    companion object {
        const val EXTRA_TASK_ID = "extra_task_id"
    }
}
