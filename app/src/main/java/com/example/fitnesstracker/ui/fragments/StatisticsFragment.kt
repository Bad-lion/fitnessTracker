package com.example.fitnesstracker.ui.fragments

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnesstracker.R
import com.example.fitnesstracker.adapter.RunAdapter
import com.example.fitnesstracker.other.CustomMarkerView
import com.example.fitnesstracker.other.SortType
import com.example.fitnesstracker.other.TrackingUtility
import com.example.fitnesstracker.ui.viewModel.MainViewModel
import com.example.fitnesstracker.ui.viewModel.StatisticViewModel
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_run.*
import kotlinx.android.synthetic.main.fragment_statistics.*
import kotlin.math.round


@AndroidEntryPoint
class StatisticsFragment : Fragment(R.layout.fragment_statistics) {

    private lateinit var runAdapter: RunAdapter

    private val viewModel : MainViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        subscribeToObserver()
        fabDeletest.setOnClickListener {
            deleteAll()
        }

        viewModel.runs.observe(viewLifecycleOwner, Observer {
            runAdapter.submitList(it)
        })


    }


    private fun subscribeToObserver() {
        viewModel.totalTimeRun.observe(viewLifecycleOwner, Observer {
            it?.let {
                val totalTimesRun = TrackingUtility.getFormattedStopWatchTime(it)
                tvTotalTime.text = totalTimesRun
            }
        })
        viewModel.totalDistance.observe(viewLifecycleOwner, Observer {
            it?.let{
                val km = it / 1000f
                val totalKm = round(km *10f) / 10f
                val totalDistance = "${totalKm} km"
                tvTotalDistance.text = totalDistance
            }
        })

        viewModel.totalAvgSpeed.observe(viewLifecycleOwner, Observer {
            it?.let {
                val avgSpeed = round (it * 10f) / 10f
                val avgSpeedStr = "${avgSpeed} km/h"
                tvAverageSpeed.text = avgSpeedStr
            }
        })

    }

    private fun setupRecyclerView() = rvRuns.apply {
        runAdapter = RunAdapter()
        adapter = runAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }



    private fun deleteAll() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_->
            viewModel.deleteRun()
            Toast.makeText(requireContext(),"Successfully remove", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){_,_->}
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure want to delete everything?")
        builder.create().show()
    }


}
