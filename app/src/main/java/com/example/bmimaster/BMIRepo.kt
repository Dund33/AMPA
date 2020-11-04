package com.example.bmimaster

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList

object BMIRepo{
    private lateinit var sharedPreferences : SharedPreferences
    private var recordLength: Int = 0


    fun init(context: Context, fileName: String, size: Int){
        sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        recordLength = size
    }

    fun addBMI(bmi: Float) {
        val listJSON = sharedPreferences.getString("saves", "[]")
        val list = Gson().fromJson<ArrayList<Float>>(listJSON, java.util.ArrayList::class.java)
        list.add(bmi)

        if(list.count() > recordLength)
            list.removeAt(0)

        val newListJSON = Gson().toJson(list)
        sharedPreferences.edit().putString("saves", newListJSON).apply()
    }

    fun getBMIs(): Array<Float> {
        val listJSON = sharedPreferences.getString("saves", "")
        val list = Gson().fromJson<ArrayList<Float>>(listJSON, java.util.ArrayList::class.java)
        return list.toTypedArray()
    }

    fun getNumberOfStoredBMIs(): Int{
        val listJSON = sharedPreferences.getString("saves", "")
        val list = Gson().fromJson<ArrayList<Float>>(listJSON, java.util.ArrayList::class.java)
        return list.count()
    }

    operator fun get(index: Int): Float {
        val listJSON = sharedPreferences.getString("saves", "")
        val list = Gson().fromJson<ArrayList<Float>>(listJSON, java.util.ArrayList::class.java)
        return list[index]
    }
}