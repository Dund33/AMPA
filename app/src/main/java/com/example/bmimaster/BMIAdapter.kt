package com.example.bmimaster

import android.graphics.Canvas
import android.graphics.Color
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BMIAdapter() : RecyclerView.Adapter<BMIAdapter.BMIViewHolder>() {
    class BMIViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bmiInfoTextView: TextView = itemView.findViewById(R.id.bmiinfoTextView)
        val square: LiterallyACircle = itemView.findViewById(R.id.literallyACircle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BMIViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bmi_info, parent, false)
        return BMIViewHolder(view)
    }

    override fun onBindViewHolder(holder: BMIViewHolder, position: Int) {
        val bmiRecord = BMIRepo[position]
        holder.bmiInfoTextView.text = bmiRecord.bmi.toString()
        holder.square.color = bmiRecord.color
    }

    override fun getItemCount(): Int {
        return BMIRepo.getNumberOfStoredBMIs()
    }
}