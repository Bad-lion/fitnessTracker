package com.example.fitnesstracker.ui.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesstracker.db.Run
import com.example.fitnesstracker.other.SortType
import com.example.fitnesstracker.repositori.MainRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @ViewModelInject constructor(
    val mainRepository: MainRepository
): ViewModel() {

    private val runSortedByDate = mainRepository.getAllSortByData()
    private val runSortedByAvgSpeed = mainRepository.getAllSortByAvgSpeed()
    private val runSortedByDistance = mainRepository.getAllSortByDistance()
    private val runSortedByMilliSeconds = mainRepository.getAllSortByMilliSeconds()

    val runs = MediatorLiveData<List<Run>>()

    var sortType = SortType.DATE

    init {
        runs.addSource(runSortedByDate){result ->
            if (sortType == SortType.DATE) {
                result?.let { runs.value = it }
            }
        }
        runs.addSource(runSortedByAvgSpeed){result ->
            if (sortType == SortType.AVG_SPEED) {
                result?.let { runs.value = it }
            }
        }
        runs.addSource(runSortedByDistance){result ->
            if (sortType == SortType.DISTANCE) {
                result?.let { runs.value = it }
            }
        }
        runs.addSource(runSortedByMilliSeconds){result ->
            if (sortType == SortType.RUNNING_TIME) {
                result?.let { runs.value = it }
            }
        }

    }

    fun sortRuns(sortType: SortType) = when (sortType) {
        SortType.DATE -> runSortedByDate.value?.let { runs.value = it }
        SortType.AVG_SPEED -> runSortedByAvgSpeed.value?.let { runs.value = it }
        SortType.DISTANCE -> runSortedByDistance.value?.let { runs.value = it }
        SortType.RUNNING_TIME -> runSortedByMilliSeconds.value?.let { runs.value = it }
    }.also {
        this.sortType = sortType
    }

    fun insertRun(run: Run) = viewModelScope.launch {
        mainRepository.insertRun(run)
    }

    fun deleteRun() = viewModelScope.launch {
        mainRepository.deleteRun()
    }


    val totalTimeRun = mainRepository.getTotalTimesRun()
    val totalAvgSpeed = mainRepository.getTotalAvgSpeed()
    val totalDistance = mainRepository.getTotalDistance()

    val sortedByDate = mainRepository.getAllSortByData()
}