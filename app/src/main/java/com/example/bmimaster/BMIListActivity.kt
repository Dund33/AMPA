package com.example.bmimaster

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BMIListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_m_i_list)
        val rv = findViewById<RecyclerView>(R.id.rvBMI)
        val adapter = BMIAdapter()
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)
    }
}