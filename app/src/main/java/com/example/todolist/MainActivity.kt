package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var tvTotalTask: TextView
    private lateinit var btnTambahTask: Button
    private lateinit var btnMulaiFokus: Button
    private lateinit var recyclerViewTask: RecyclerView
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi view
        tvTotalTask = findViewById(R.id.tvTotalTask)
        btnTambahTask = findViewById(R.id.btnTambahTask)
        btnMulaiFokus = findViewById(R.id.btnMulaiFokus)
        recyclerViewTask = findViewById(R.id.recyclerViewTask)

        // Setup RecyclerView
        recyclerViewTask.layoutManager = LinearLayoutManager(this)

        taskAdapter = TaskAdapter(TaskRepository.getAllTasks()) { task ->
            val intent = Intent(this, DetailTaskActivity::class.java)
            intent.putExtra("task_id", task.id) // 🔥 ini yang penting
            startActivity(intent)
        }

        recyclerViewTask.adapter = taskAdapter

        // Tombol tambah task
        btnTambahTask.setOnClickListener {
            startActivity(Intent(this, AddTaskActivity::class.java))
        }

        // Tombol fokus belajar
        btnMulaiFokus.setOnClickListener {
            startActivity(Intent(this, FocusActivity::class.java))
        }

        updateTotalTask()
    }

    override fun onResume() {
        super.onResume()

        // Refresh data saat kembali dari halaman lain
        taskAdapter = TaskAdapter(TaskRepository.getAllTasks()) { task ->
            val intent = Intent(this, DetailTaskActivity::class.java)
            intent.putExtra("task_id", task.id)
            startActivity(intent)
        }

        recyclerViewTask.adapter = taskAdapter
        updateTotalTask()
    }

    private fun updateTotalTask() {
        val total = TaskRepository.getAllTasks().size
        tvTotalTask.text = "Total tugas: $total"
    }
}