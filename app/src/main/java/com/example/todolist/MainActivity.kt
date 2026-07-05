package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.adapter.TaskAdapter
import com.example.todolist.data.TaskDatabaseHelper

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: TaskDatabaseHelper
    private lateinit var adapter: TaskAdapter
    private lateinit var rvTasks: RecyclerView
    private lateinit var etSearch: EditText
    private lateinit var spFilter: Spinner
    private lateinit var tvEmpty: TextView

    private var currentKeyword = ""
    private var currentFilter = "Semua"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = TaskDatabaseHelper(this)
        setupView()
        setupRecyclerView()
        setupFilter()
        setupSearch()
        loadTasks()
    }

    override fun onResume() {
        super.onResume()
        loadTasks()
    }

    private fun setupView() {
        rvTasks = findViewById(R.id.rvTasks)
        etSearch = findViewById(R.id.etSearch)
        spFilter = findViewById(R.id.spFilter)
        tvEmpty = findViewById(R.id.tvEmpty)

        findViewById<Button>(R.id.btnAddTask).setOnClickListener {
            startActivity(Intent(this, AddEditTaskActivity::class.java))
        }

        findViewById<Button>(R.id.btnFocus).setOnClickListener {
            startActivity(Intent(this, FocusTimerActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
        adapter = TaskAdapter(
            emptyList(),
            onItemClick = { task ->
                val intent = Intent(this, DetailTaskActivity::class.java)
                intent.putExtra(DetailTaskActivity.EXTRA_TASK_ID, task.id)
                startActivity(intent)
            },
            onStatusChanged = { task, isDone ->
                dbHelper.updateTaskStatus(task.id, isDone)
                Toast.makeText(this, "Status tugas diperbarui", Toast.LENGTH_SHORT).show()
                loadTasks()
            }
        )

        rvTasks.layoutManager = LinearLayoutManager(this)
        rvTasks.adapter = adapter
    }

    private fun setupFilter() {
        val filters = listOf("Semua", "Belum Selesai", "Selesai", "Prioritas Tinggi")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, filters)
        spFilter.adapter = spinnerAdapter
        spFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currentFilter = filters[position]
                loadTasks()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupSearch() {
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                currentKeyword = s.toString()
                loadTasks()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun loadTasks() {
        val tasks = dbHelper.getAllTasks(currentKeyword, currentFilter)
        adapter.updateData(tasks)
        tvEmpty.visibility = if (tasks.isEmpty()) View.VISIBLE else View.GONE
    }
}
