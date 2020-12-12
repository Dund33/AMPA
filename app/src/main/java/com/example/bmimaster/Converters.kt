package com.example.bmimaster

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return if (date == null) null else date.getTime()
    }

    @TypeConverter
    fun measureSystemToInt(measureSystem: MeasureSystem) = when(measureSystem){
        MeasureSystem.Imperial -> 0
        MeasureSystem.Metric -> 1
    }

    @TypeConverter
    fun intToMeasureSystem(measureSystem: Int) = when(measureSystem){
        0 -> MeasureSystem.Imperial
        1 -> MeasureSystem.Metric
        else -> null
    }
}