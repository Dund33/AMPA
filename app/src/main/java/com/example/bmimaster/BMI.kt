package com.example.bmimaster

import android.graphics.Color
import kotlin.math.pow

class BMI {

    companion object {

        fun getBMIDescription(bmi: Double): String{
            return when (bmi) {
                in 12.0..19.0 -> "Underweight"
                in 19.0..24.0 -> "OK"
                in 24.0..29.0 -> "Overweight"
                in 29.0..43.0 -> "Obese"
                else -> "Allah have mercy"
            }
        }

        fun getBMI(height: Double, weight: Double, measureSystem: MeasureSystem): Pair<Double, Int> {

            val bmi = when(measureSystem){
                MeasureSystem.Metric -> weight / (height/100).pow(2)
                MeasureSystem.Imperial -> 703.0 * weight / (height).pow(2)
            }

            val color = when (bmi) {
                in 12.0..19.0 -> Color.BLUE
                in 19.0..24.0 -> Color.GREEN
                in 24.0..29.0 -> Color.YELLOW
                in 29.0..43.0 -> Color.RED
                else -> Color.RED
            }
            return Pair(bmi, color)
        }
    }
}