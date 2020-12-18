package com.example.fitnesstracker.ui.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.fitnesstracker.repositori.MainRepository
import javax.inject.Inject

class StatisticViewModel @ViewModelInject constructor(
    val mainRepository: MainRepository
): ViewModel() {


}