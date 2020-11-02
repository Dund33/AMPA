package com.example.bmimaster

import android.content.Context
import java.util.*

class BMIRepo private constructor(
    fileName: String,
    context: Context,
    val recordLength: Int
) {
    private val sharedPreferences =
        context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    private var storedBMIs =
        sharedPreferences.getInt("stored_bmis", 0)


    fun addBMI(bmi: Float) {
        if (storedBMIs < recordLength) {
            sharedPreferences.edit()
                .putFloat(storedBMIs.toString(), bmi)
                .apply()
            storedBMIs++
        } else {
            for (i in 1..recordLength) {
                val nextBMI = sharedPreferences.getFloat(i.toString(), -1.0f)
                sharedPreferences.edit()
                    .putFloat((i - 1).toString(), nextBMI)
                    .apply()
            }
            sharedPreferences.edit()
                .putFloat((recordLength - 1).toString(), bmi)
                .apply()
        }
    }

    fun getBMIs(): Array<Float> {
        val list = LinkedList<Float>()
        for (i in 0..10) {
            val bmi = sharedPreferences.getFloat(i.toString(), 0f)
            list.add(bmi)
        }
        return list.toTypedArray()
    }

    operator fun get(index: Int): Float {
        val list = LinkedList<Float>()
        for (i in 0..10) {
            val bmi = sharedPreferences.getFloat(i.toString(), 0f)
            list.add(bmi)
        }
        return list.toTypedArray()[index]
    }

    companion object{
        private var instance : BMIRepo? = null

        fun getInstance(context:Context): BMIRepo{
            if(instance == null){
                instance = BMIRepo("data", context, 10)
            }
            return instance as BMIRepo
        }
    }
}