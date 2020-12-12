package com.example.bmimaster

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(entities = [BMIRecord::class], version = 2)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase(){
    abstract fun getDAO(): BmiRecordDAO
}