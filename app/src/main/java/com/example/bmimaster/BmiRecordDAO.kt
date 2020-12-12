package com.example.bmimaster

import androidx.room.*

@Dao
interface BmiRecordDAO {
    @Insert
    fun insert(record: BMIRecord)
    @Update
    fun update(record: BMIRecord)
    @Delete
    fun delete(record: BMIRecord)
    @Query("SELECT COUNT(*) FROM records")
    fun count(): Int
    @Query("SELECT * FROM records where id = :id")
    fun get(id: Int): BMIRecord
}