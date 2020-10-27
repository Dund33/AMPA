package com.example.bmimaster

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.adapters.TextViewBindingAdapter

class Details : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val bmi = intent.getCharSequenceExtra("bmi")
        val bmiTV = findViewById<TextView>(R.id.bmiTV)
        bmiTV.text = bmi
    }

}