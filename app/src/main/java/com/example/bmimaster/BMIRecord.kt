package com.example.bmimaster

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "records")
class BMIRecord (@PrimaryKey(autoGenerate = true) @NonNull val id: Int, val bmi: Float, val color: Int, val measureSystem: MeasureSystem, val date: Date)