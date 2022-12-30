package com.example.todoapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.RowItemsBinding

class TaskAdapter(var taskList: MutableList<Task>)
    :RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    var onItemClick : ((Task)-> Unit)? = null

    var onUpdateButtonClick : ((Task,Int)-> Unit)? = null
    //delete
    var onDeleteButtonClick: ((Task,Int)-> Unit)? = null

    inner class TaskViewHolder(val binding: RowItemsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowItemsBinding.inflate(layoutInflater,parent,false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.binding.apply {
            tvTitle.text = taskList[position].title
            tvDesc.text = taskList[position].desc
            textViewNoteDate.text = taskList[position].dateOfCreation
            textViewNoteTime.text = taskList[position].timeOfCreation

            btnImgEdit.setOnClickListener() {
                onUpdateButtonClick?.invoke(taskList[position], position)
            }
            btnImgDelete.setOnClickListener {
                onDeleteButtonClick?.invoke(taskList[position], position)
            }
        }
        holder.itemView.setOnClickListener(){
            onItemClick?.invoke(taskList[position])
        }

    }

    override fun getItemCount(): Int {
        return taskList.size
    }
}