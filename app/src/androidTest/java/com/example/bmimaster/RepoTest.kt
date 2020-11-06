package com.example.bmimaster
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepoTest {

    @Before
    fun initRepo(){

    }

    @Test
    fun emptyRepoHasNoBMIs(){
        //Arrange
        val oldRepo = BMIRepo.repoFile
        BMIRepo.repoFile = "test"

        //Act
        val storedBMIs = BMIRepo.getNumberOfStoredBMIs()

        //Assert
        assert(storedBMIs == 0)

        //Cleanup
        BMIRepo.removeFile()
        BMIRepo.repoFile = oldRepo
    }

    @Test
    fun addedOneRecordToRepo(){
        //Arrange
        val oldRepo = BMIRepo.repoFile
        BMIRepo.repoFile = "test"

        //Act
        BMIRepo.addBMI(3.3f)

        //Assert
        assert(BMIRepo.getNumberOfStoredBMIs() == 1)

        //Cleanup
        BMIRepo.removeFile()
        BMIRepo.repoFile = oldRepo
    }

    @Test
    fun addedMoreThanTenRecordsToRepo(){
        //Arrange
        val oldRepo = BMIRepo.repoFile
        BMIRepo.repoFile = "test"

        //Act
        for(i in 0..11)
            BMIRepo.addBMI(i.toFloat())

        //Assert
        for(i in 0..10)
            Assert.assertEquals(i.toFloat(), BMIRepo[i].bmi, 0.01f)

        //Cleanup
        BMIRepo.removeFile()
        BMIRepo.repoFile = oldRepo
    }
}