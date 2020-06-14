package com.example.nestedRecyclerViewSwipeMenu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mockData()
    }

    private fun initViewAdapter(tasks: ArrayList<HashMap<String, String>>) {
        val rv = findViewById<RecyclerView>(R.id.rvTasks)
        rv.layoutManager = LinearLayoutManager(this)

        val adapter = TasksAdapter(tasks)
        rv.adapter = adapter
    }

    private fun mockData() {
        // Initialize test locations
        val tasks: ArrayList<HashMap<String, String>> = ArrayList()
        val time = System.currentTimeMillis()
        // Load items into ArrayList
        tasks.add(
            hashMapOf(
                "task" to "Learn Java",
                "difficulty" to "Medium",
                "time" to time.toString()
            )
        )
        tasks.add(
            hashMapOf(
                "task" to "Learn Kotlin",
                "difficulty" to "Medium",
                "time" to time.toString()
            )
        )
        tasks.add(
            hashMapOf(
                "task" to "Work with espresso",
                "difficulty" to "Master",
                "time" to time.toString()
            )
        )

        val tomorrowTime = time + (1000 * 60 * 60 * 24 * 3)
        tasks.add(
            hashMapOf(
                "task" to "Build web view",
                "difficulty" to "Easy",
                "time" to tomorrowTime.toString()
            )
        )
        tasks.add(
            hashMapOf(
                "task" to "Doc for Android different pattern",
                "difficulty" to "Master",
                "time" to tomorrowTime.toString()
            )
        )
        tasks.add(
            hashMapOf(
                "task" to "Look for Android webservices",
                "difficulty" to "Master",
                "time" to tomorrowTime.toString()
            )
        )
        // Bind items to RecyclerView
        initViewAdapter(tasks)
    }
}
