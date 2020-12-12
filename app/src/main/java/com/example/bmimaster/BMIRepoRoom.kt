package com.example.bmimaster

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import java.util.*

object BMIRepoRoom {


    private lateinit var dao: BmiRecordDAO

    fun init(context: Context){
        val room = Room.databaseBuilder(context, Database::class.java, "db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
        dao = room.getDAO()
    }

    fun addBMI(bmi: Float, measureSystem: MeasureSystem, date: Date) {
        val record = BMIRecord(0, bmi, BMI.getColor(bmi),measureSystem, date)
        dao.insert(record)
    }

    fun getNumberOfStoredBMIs() = dao.count()

    operator fun get(index: Int) = dao.get(index+1)
}