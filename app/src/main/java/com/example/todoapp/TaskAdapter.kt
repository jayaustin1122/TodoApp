package com.example.todoapp

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.RowItemsBinding
import kotlin.random.Random

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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.binding.apply {
            tvTitle.text = taskList[position].title
            tvDesc.text = taskList[position].desc
            textViewNoteDate.text = taskList[position].dateOfCreation
            textViewNoteTime.text = taskList[position].timeOfCreation
            //listLayout.setBackgroundColor(holder.itemView.resources.getColor(randomColor(),null))
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

    fun randomColor():Int{
        val list = ArrayList<Int>()
        list.add(R.color.One)
        list.add(R.color.two)
        list.add(R.color.three)
        list.add(R.color.gray)
        list.add(R.color.four)
        list.add(R.color.six)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(list.size)
        return list[randomIndex]

    }
}