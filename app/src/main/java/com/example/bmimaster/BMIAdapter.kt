package com.example.bmimaster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BMIAdapter(private val measurements: Array<Int>) : RecyclerView.Adapter<BMIAdapter.BMIViewHolder>() {
    class BMIViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bmiInfoTextView: TextView = itemView.findViewById(R.id.bmiinfoTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BMIViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.bmi_info, parent, false)
        return BMIViewHolder(view)
    }

    override fun onBindViewHolder(holder: BMIViewHolder, position: Int) {
        holder.bmiInfoTextView.text = measurements[position].toString()
    }

    override fun getItemCount(): Int {
        return measurements.size
    }
}