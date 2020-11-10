package com.example.bmimaster

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList

object BMIRepo{
    private lateinit var sharedPreferences : SharedPreferences
    private var recordLength: Int = 0
    var repoFile = "saves"

    fun init(context: Context, fileName: String, size: Int){
        sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        recordLength = size
        repoFile = fileName
    }

    fun addBMI(bmi: Float, measureSystem: MeasureSystem, date: Date) {
        val list = getBMIs()
        val color = BMI.getColor(bmi)
        list.addFirst(BMIRecord(bmi, color, measureSystem, date))

        if(list.count() > recordLength)
            list.removeLast()

        val array = list.toTypedArray()
        val newListJSON = Gson().toJson(array)
        sharedPreferences.edit().putString(repoFile, newListJSON).apply()
    }

    fun removeFile(){
        sharedPreferences
                .edit()
                .remove(repoFile)
                .apply()
    }

    private fun getBMIs(): LinkedList<BMIRecord> {
        val listJSON = sharedPreferences.getString(repoFile, "[]")
        val list =  Gson().fromJson(listJSON, Array<BMIRecord>::class.java)
        val linkedList = LinkedList<BMIRecord>()
        linkedList.addAll(list)
        return linkedList
    }

    fun getNumberOfStoredBMIs(): Int{
        return getBMIs().count()
    }

    operator fun get(index: Int): BMIRecord {
        val list = getBMIs()
        return list[index]
    }
}