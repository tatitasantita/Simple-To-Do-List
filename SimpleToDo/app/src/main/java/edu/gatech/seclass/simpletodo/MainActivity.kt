package edu.gatech.seclass.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                // remove item from list
                listOfTasks.removeAt(position)

                // notify adapter something changed
                adapter.notifyDataSetChanged()

                saveItems()
            }
        }

        loadItems()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView) as RecyclerView
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val inputTextField=  findViewById<EditText>(R.id.addTaskField)

        findViewById<Button>(R.id.button).setOnClickListener{
            // grab the text the user has inputted into @id/addTaskField
            val userInputtedTask = inputTextField.text.toString()

            // Add the string to our list of tasks: listOfTasks'
            listOfTasks.add(userInputtedTask)

            // notify
            adapter.notifyItemInserted(listOfTasks.size - 1)

            // reset text field
            inputTextField.setText("")

            // save to file
            saveItems()
        }
    }

    // save the data the user input
    // save data by writing and reading from a file

    // get the file
    fun getDataFile(): File {
        // every line is going to represent a task in list
        return File(filesDir, "data.txt")
    }

    //load the items by reading every line in the data file
    fun loadItems(){
        try{
            listOfTasks= FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }
        catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }

    // save items by writing them into our data files
    fun saveItems(){
        try{
            FileUtils.writeLines(getDataFile(), listOfTasks)
        }
        catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }
}