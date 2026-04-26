package com.example.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val taskList: List<Task>,
    private val onItemClick: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardTask: CardView = itemView.findViewById(R.id.cardTask)
        val tvTaskTitle: TextView = itemView.findViewById(R.id.tvTaskTitle)
        val tvTaskSubject: TextView = itemView.findViewById(R.id.tvTaskSubject)
        val tvTaskDeadline: TextView = itemView.findViewById(R.id.tvTaskDeadline)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]

        holder.tvTaskTitle.text = task.title
        holder.tvTaskSubject.text = "Mapel: ${task.subject}"
        holder.tvTaskDeadline.text = "Deadline: ${task.deadline}"

        holder.cardTask.setOnClickListener {
            holder.cardTask.animate()
                .scaleX(0.97f)
                .scaleY(0.97f)
                .setDuration(100)
                .withEndAction {
                    holder.cardTask.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(100)
                        .withEndAction {
                            onItemClick(task)
                        }
                        .start()
                }
                .start()
        }
    }

    override fun getItemCount(): Int = taskList.size
}