package com.example.bmimaster
import android.content.Context
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepoTest {

    private val repoSize = 10
    private val overflowSize = 1000
    @Before
    fun initRepo(){
        //Arrange
        val context = getApplicationContext<Context>()
        BMIRepo.init(context, "test", repoSize)
        BMIRepo.removeFile()
    }

    @After
    fun cleanup(){
        //Cleanup
        BMIRepo.removeFile()
    }

    @Test
    fun emptyRepoHasNoBMIs(){
        //Act
        val storedBMIs = BMIRepo.getNumberOfStoredBMIs()
        //Assert
        assert(storedBMIs == 0)
    }

    @Test
    fun addedOneRecordToRepo(){
        //Act
        BMIRepo.addBMI(3.3f)
        //Assert
        assert(BMIRepo.getNumberOfStoredBMIs() == 1)
    }

    @Test
    fun testRepoOverflowNumber(){
        //Act
        for(i in 1..overflowSize){
            BMIRepo.addBMI(i.toFloat())
        }
        //Assert
        Assert.assertEquals(BMIRepo.getNumberOfStoredBMIs(), repoSize)
    }

    @Test
    fun testRepoOverflowNoException(){
        //Act/Assert
        for(i in 1..overflowSize){
            BMIRepo.addBMI(i.toFloat())
        }
    }

    @Test
    fun addedMoreThanTenRecordsToRepo(){
        //Act
        for(i in 0..repoSize)
            BMIRepo.addBMI(i.toFloat())
        //Assert
        for(i in 0 until repoSize)
            Assert.assertEquals((i+1).toFloat(), BMIRepo[repoSize-1-i].bmi, 0.01f)
    }
}