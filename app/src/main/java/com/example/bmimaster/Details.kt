package com.example.bmimaster

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.adapters.TextViewBindingAdapter
import org.w3c.dom.Text

class Details : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val bmiTV = findViewById<TextView>(R.id.bmiTV)
        val bmiDescriptionTV = findViewById<TextView>(R.id.descriptionTV)

        val bmi = intent.getDoubleExtra("bmi", 0.0)

        val bmiDescription = BMI.getBMIDescription(bmi)
        bmiDescriptionTV.text = bmiDescription

        bmiTV.text = bmi.toString()
    }

}