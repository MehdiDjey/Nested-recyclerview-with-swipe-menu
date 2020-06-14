package com.example.nestedRecyclerViewSwipeMenu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class TaskAdapter(private val tasks: ArrayList<HashMap<String, String>>) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskName.text = task["task"]
        holder.taskDifficulty.text = task["difficulty"]
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item_locations, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskName: TextView = itemView.findViewById(R.id.tvTaskName)
        val taskDifficulty: TextView = itemView.findViewById(R.id.tvTaskDifficult)
    }


}