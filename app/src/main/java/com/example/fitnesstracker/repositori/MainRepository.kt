package com.example.fitnesstracker.repositori

import com.example.fitnesstracker.db.Run
import com.example.fitnesstracker.db.RunDao
import javax.inject.Inject

class MainRepository @Inject constructor(
    val runDao: RunDao
) {

    suspend fun insertRun(run: Run) = runDao.insertRun(run)

    suspend fun deleteRun() = runDao.deleteRun()

    fun getAllSortByData() = runDao.getAllSortByDate()

    fun getAllSortByDistance() = runDao.getAllSortByDistance()

    fun getAllSortByAvgSpeed() = runDao.getAllSortByAvgSpeed()

    fun getAllSortByMilliSeconds() = runDao.getAllSortByMilliSeconds()

    fun getTotalAvgSpeed() = runDao.getTotalAverageSpeed()

    fun getTotalDistance() = runDao.getTotalDistance()

    fun getTotalTimesRun() = runDao.getTotalTimesRun()

}