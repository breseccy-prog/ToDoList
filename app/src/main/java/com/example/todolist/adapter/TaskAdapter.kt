package com.example.todolist.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.data.Task

class TaskAdapter(
    private var tasks: List<Task>,
    private val onItemClick: (Task) -> Unit,
    private val onStatusChanged: (Task, Boolean) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    fun updateData(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cbDone: CheckBox = itemView.findViewById(R.id.cbDone)
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvInfo: TextView = itemView.findViewById(R.id.tvInfo)
        private val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)

        fun bind(task: Task) {
            cbDone.setOnCheckedChangeListener(null)
            cbDone.isChecked = task.isDone

            tvTitle.text = task.title
            tvInfo.text = "${task.category} • ${task.priority} • Deadline: ${task.deadline}"
            tvStatus.text = if (task.isDone) "Selesai" else "Belum selesai"

            if (task.isDone) {
                tvTitle.paintFlags = tvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                tvTitle.paintFlags = tvTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

            itemView.setOnClickListener { onItemClick(task) }
            cbDone.setOnCheckedChangeListener { _, isChecked ->
                onStatusChanged(task, isChecked)
            }
        }
    }
}
