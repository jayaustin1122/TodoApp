package com.example.todoapp

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.databinding.AddDialogBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    private val dao = TaskDao()
    lateinit var adapter: TaskAdapter
    private val list:ArrayList<Task> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = TaskAdapter(list)
        view()
        binding.btnAdd.setOnClickListener(){
            addDialog()
        }
        adapter.onItemClick = {

            Toast.makeText(applicationContext, "itemclicked!", Toast.LENGTH_SHORT).show()
        }
        adapter.onDeleteButtonClick = { item:Task , position:Int ->
            Toast.makeText(applicationContext, "delete button!", Toast.LENGTH_SHORT).show()

        }
        adapter.onUpdateButtonClick = { item:Task , position:Int ->
            Toast.makeText(applicationContext, "update button!", Toast.LENGTH_SHORT).show()
        }





    }
    private fun view() {
        dao.get().addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                var dataFromDatabase = snapshot.children

                for(data in dataFromDatabase){
                    //get id of the object from DB
                    var title = data.child("title").value.toString()
                    var desc = data.child("desc").value.toString()
                    var currentDate = data.child("dateOfCreation").value.toString()
                    var currentTime = data.child("timeOfCreation").value.toString()

                    var employee = Task(title,desc,currentDate,currentTime)
                    list.add(employee)
                }
                adapter.onItemClick = {

                    Toast.makeText(applicationContext, "itemclicked!", Toast.LENGTH_SHORT).show()
                }
                adapter.onDeleteButtonClick = { item:Task , position:Int ->
                    Toast.makeText(applicationContext, "delete button!", Toast.LENGTH_SHORT).show()

                }
                adapter.onUpdateButtonClick = { item:Task , position:Int ->
                    Toast.makeText(applicationContext, "update button!", Toast.LENGTH_SHORT).show()
                }



                adapter = TaskAdapter(list)
                binding.myRecyclerView.adapter = TaskAdapter(list)
                binding.myRecyclerView.adapter = adapter
                binding.myRecyclerView.layoutManager = LinearLayoutManager(applicationContext)



            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun getCurrentTime(): String {
        val tz = TimeZone.getTimeZone("GMT+08:00")
        val c = Calendar.getInstance(tz)
        val hours = String.format("%02d", c.get(Calendar.HOUR))
        val minutes = String.format("%02d", c.get(Calendar.MINUTE))
        return "$hours:$minutes"
    }


    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDate() : String {
        val currentDateObject = Date()
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        return formatter.format(currentDateObject)
    }

    private fun addDialog() {
        val currentTime = getCurrentTime()
        val currentDate1 = getCurrentDate()
        val dialog = Dialog(this)
        val binding: AddDialogBinding = AddDialogBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.show()
        binding.btnAdd2.setOnClickListener() {
            if(binding.etAddTitle.length() >=1 && binding.etAddDescription.length()>=1 ) {
                dao.add(Task(
                    binding.etAddTitle.text.toString().capitalize(),
                    binding.etAddDescription.text.toString(),
                    currentDate1,
                    currentTime))

                binding.etAddTitle.text?.clear()
                binding.etAddDescription.text?.clear()

                Toast.makeText(applicationContext, "Saved!", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }else{
                Toast.makeText(applicationContext, "You cannot add a empty activity!", Toast.LENGTH_LONG).show()
            }

        }

    }
}