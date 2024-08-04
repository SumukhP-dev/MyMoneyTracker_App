package com.example.mymoneytracker.ui.summary

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mymoneytracker.MainActivity
import com.example.mymoneytracker.R
import com.example.mymoneytracker.databinding.FragmentSummaryBinding
import com.example.mymoneytracker.ui.OnSwipeTouchListener
import org.eazegraph.lib.models.PieModel


class SummaryFragment : Fragment() {
    private lateinit var viewModel: SummaryViewModel

    private var _binding: FragmentSummaryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSummaryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel = ViewModelProvider(this).get(SummaryViewModel::class.java)

        // This implements swipe gestures to go to Home Fragment
        binding.nestedScrollView.setOnTouchListener(object: OnSwipeTouchListener(context) {
            override fun onSwipeRight() {
                findNavController().navigate(R.id.action_summaryFragment_to_nav_home)
            }
            override fun onSwipeLeft() {
            }
        })

        (activity as MainActivity).supportActionBar?.title = "Summary"

        binding.backSummaryButton.setOnClickListener {
            findNavController().navigate(R.id.action_summaryFragment_to_nav_home)
        }

        viewModel.displayTips(viewModel.user.getNetWorthCalculated())
        binding.tips.text = viewModel.user.getTipsMessages()

        binding.progressBar.progress = viewModel.getProgress(viewModel.user.getNetWorthCalculated())

        initializePieChart()

        binding.progressBar.max = 100
        binding.progressBar.progressTintList = ColorStateList.valueOf(Color.GREEN);

        return root
    }

    fun initializePieChart() {
        for (element in viewModel.user.getDataForPieChart()) {
            if (element != 0.0) {
                setPieChart(viewModel.user.getDataForPieChart())
                return
            }
        }
        setPieChartDefault()
    }

    fun setPieChartDefault(){
        binding.pieChart.addPieSlice(
            PieModel(
                0F, Color.parseColor("#A9A9A9")
            )
        )
    }

    fun setPieChart(dataForPieChart: Array<Double>) {
        // Set the data and color to the pie chart
        binding.pieChart.addPieSlice(
            PieModel(
                "Rent/Mortgage", dataForPieChart[0].toFloat(),
                Color.parseColor("#FFA726")
            )
        )
        binding.pieChart.addPieSlice(
            PieModel(
                "Utilities", dataForPieChart[1].toFloat(),
                Color.parseColor("#66BB6A")
            )
        )
        binding.pieChart.addPieSlice(
            PieModel(
                "Student Loans", dataForPieChart[2].toFloat(),
                Color.parseColor("#EF5350")
            )
        )
        binding.pieChart.addPieSlice(
            PieModel(
                "Car payments", dataForPieChart[3].toFloat(),
                Color.parseColor("#29B6F6")
            )
        )
        binding.pieChart.addPieSlice(
            PieModel(
                "Food", dataForPieChart[4].toFloat(),
                Color.parseColor("#673AB7")
            )
        )
        binding.pieChart.addPieSlice(
            PieModel(
                "Fun", dataForPieChart[5].toFloat(),
                Color.parseColor("#009688")
            )
        )
        binding.pieChart.addPieSlice(
            PieModel(
                "Miscellaneous", dataForPieChart[6].toFloat(),
                Color.parseColor("#3F51B5")
            )
        )
    }
}