package com.example.todolist

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetailTaskActivity : AppCompatActivity() {

    private lateinit var tvDetailTitle: TextView
    private lateinit var tvDetailSubject: TextView
    private lateinit var tvDetailDeadline: TextView
    private lateinit var tvDetailDescription: TextView
    private lateinit var btnHapusTask: Button
    private lateinit var btnKembaliDetail: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_task)

        tvDetailTitle = findViewById(R.id.tvDetailTitle)
        tvDetailSubject = findViewById(R.id.tvDetailSubject)
        tvDetailDeadline = findViewById(R.id.tvDetailDeadline)
        tvDetailDescription = findViewById(R.id.tvDetailDescription)
        btnHapusTask = findViewById(R.id.btnHapusTask)
        btnKembaliDetail = findViewById(R.id.btnKembaliDetail)

        val taskId = intent.getIntExtra("task_id", -1)
        val task = TaskRepository.getTaskById(taskId)

        if (task == null) {
            Toast.makeText(this, "Tugas tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        tvDetailTitle.text = task.title
        tvDetailSubject.text = "Mata Pelajaran: ${task.subject}"
        tvDetailDeadline.text = "Deadline: ${task.deadline}"
        tvDetailDescription.text = task.description

        btnHapusTask.setOnClickListener {
            TaskRepository.deleteTask(taskId)
            Toast.makeText(this, "Tugas berhasil dihapus", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnKembaliDetail.setOnClickListener {
            finish()
        }
    }
}