package com.example.todolist

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddTaskActivity : AppCompatActivity() {

    private lateinit var etJudulTask: EditText
    private lateinit var etMapel: EditText
    private lateinit var etDeadline: EditText
    private lateinit var etDeskripsi: EditText
    private lateinit var btnSimpanTask: Button
    private lateinit var btnBatal: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        etJudulTask = findViewById(R.id.etJudulTask)
        etMapel = findViewById(R.id.etMapel)
        etDeadline = findViewById(R.id.etDeadline)
        etDeskripsi = findViewById(R.id.etDeskripsi)
        btnSimpanTask = findViewById(R.id.btnSimpanTask)
        btnBatal = findViewById(R.id.btnBatal)

        btnSimpanTask.setOnClickListener {
            saveTask()
        }

        btnBatal.setOnClickListener {
            finish()
        }
    }

    private fun saveTask() {
        val title = etJudulTask.text.toString().trim()
        val subject = etMapel.text.toString().trim()
        val deadline = etDeadline.text.toString().trim()
        val description = etDeskripsi.text.toString().trim()

        when {
            title.isEmpty() -> {
                etJudulTask.error = "Judul tugas wajib diisi"
                etJudulTask.requestFocus()
                return
            }

            subject.isEmpty() -> {
                etMapel.error = "Mata pelajaran wajib diisi"
                etMapel.requestFocus()
                return
            }

            deadline.isEmpty() -> {
                etDeadline.error = "Deadline wajib diisi"
                etDeadline.requestFocus()
                return
            }

            description.isEmpty() -> {
                etDeskripsi.error = "Deskripsi wajib diisi"
                etDeskripsi.requestFocus()
                return
            }

            title.length < 3 -> {
                etJudulTask.error = "Judul minimal 3 karakter"
                etJudulTask.requestFocus()
                return
            }

            else -> {
                TaskRepository.addTask(
                    title = title,
                    subject = subject,
                    deadline = deadline,
                    description = description
                )

                Toast.makeText(this, "Tugas berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}