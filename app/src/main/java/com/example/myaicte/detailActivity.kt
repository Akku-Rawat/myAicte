package com.example.myaicte

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class detailActivity : AppCompatActivity() {
    private lateinit var recyclerView1: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        val course = intent.getParcelableExtra<CourseRVModal>("Name")


    }

}
