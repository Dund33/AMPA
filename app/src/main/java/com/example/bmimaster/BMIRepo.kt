package com.example.bmimaster

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import kotlin.collections.ArrayList

object BMIRepo{
    private lateinit var sharedPreferences : SharedPreferences
    private var recordLength: Int = 0
    var repoFile = "saves"

    fun init(context: Context, fileName: String, size: Int){
        sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        recordLength = size
    }

    fun addBMI(bmi: Float) {
        val list = getBMIs()
        val color = BMI.getColor(bmi)
        list.add(BMIRecord(bmi, color))

        if(list.count() > recordLength)
            list.removeAt(0)

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

    private fun getBMIs(): ArrayList<BMIRecord> {
        val listJSON = sharedPreferences.getString(repoFile, "[]")
        val list =  Gson().fromJson(listJSON, Array<BMIRecord>::class.java)
        val arrayList = ArrayList<BMIRecord>()
        arrayList.addAll(list)
        return arrayList
    }

    fun getNumberOfStoredBMIs(): Int{
        return getBMIs().count()
    }

    operator fun get(index: Int): BMIRecord {
        val list = getBMIs()
        return list[index]
    }
}