package com.example.mymoneytracker.ui.home

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mymoneytracker.R
import com.example.mymoneytracker.databinding.FragmentHomeBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import java.time.LocalDate
import java.util.TreeMap
import kotlin.properties.Delegates


class HomeFragment : Fragment() {

    private var netWorth by Delegates.notNull<Int>()
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    // Test data to configure chart
    private val testDates = arrayOf<String>("12/01/2007", "12/02/2007", "12/01/2008")
    private val testAmounts = arrayOf<Int>(100, -60, -60)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.HistoryButton.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_historyFragment)
        }

        binding.SummaryButton.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_summaryFragment)
        }

        fun netWorthColorChange(netWorth: Int) {
            if (netWorth >= 0) {
                binding.NetWorthAmount.setTextColor(Color.parseColor("#00FF0A"))
                binding.NetWorthAmount.text = "$" + netWorth.toString()
            } else {
                binding.NetWorthAmount.setTextColor(Color.parseColor("#FF1100"))
                binding.NetWorthAmount.text = "($" + netWorth.toString() + ")"
            }
        }

        setFragmentResultListener("requestKey2") { requestKey, bundle ->
            netWorth = bundle.getInt("bundleKey2")
            binding.NetWorthAmount.text = netWorth.toString()
            netWorthColorChange(netWorth)
        }

        val chart = binding.chart as BarChart

        val netWorthArray = getNetWorthArray(testAmounts)
        val dateArray = getArrayofDateObjects(testDates)

        var treeMap = TreeMap<LocalDate, Int>()
        for (i in netWorthArray.indices) {
            treeMap.put(dateArray[i], netWorthArray[i])
        }

        barChart(treeMap)

        return root
    }

    // This function creates a bar chart to show net worth over a period of time
    fun barChart(treeMapToAdd: TreeMap<LocalDate, Int>) {
        var mChart: BarChart = binding.chart
        mChart.setDrawBarShadow(false)
        mChart.getDescription().setEnabled(false)
        mChart.setPinchZoom(false)
        mChart.setDrawGridBackground(true)

        var labels = emptyArray<String>()
        treeMapToAdd.navigableKeySet().forEach {label ->
            labels += label.toString()
        }
        labels += ""

        // The xAxis variable defines the x-axis values of the chart
        val xAxis: XAxis = mChart.getXAxis()
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(true)
        xAxis.granularity = 1f
        xAxis.textColor = Color.BLACK
        xAxis.textSize = 12f
        xAxis.axisLineColor = Color.WHITE
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)

        // The leftAxis variable defines the y-axis values of the chart
        val leftAxis: YAxis = mChart.getAxisLeft()
        leftAxis.textColor = Color.BLACK
        leftAxis.textSize = 12f
        leftAxis.axisLineColor = Color.WHITE
        leftAxis.setDrawGridLines(true)
        leftAxis.granularity = 2f
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        mChart.getAxisRight().setEnabled(false)
        mChart.getLegend().setEnabled(false)
        val valOne = treeMapToAdd.values.toList()
        var barOne = mutableListOf<BarEntry>()
        val dataSets = ArrayList<IBarDataSet>()
        for (i in valOne.indices) {
            barOne += (BarEntry(i.toFloat(), valOne[i].toFloat()))
            val set = BarDataSet(barOne.toMutableList(), "barOne")

            // This if check determines if the bar should be green or red
            if (valOne[i].toFloat() >= 0) {
                set.color = Color.GREEN
            } else {
                set.color = Color.RED
            }
            set.isHighlightEnabled = false
            set.setDrawValues(false)
            dataSets.add(set)
            barOne = mutableListOf<BarEntry>()
        }
        val data = BarData(dataSets)
        val barWidth = 0.3f
        data.barWidth = barWidth
        xAxis.axisMaximum = labels.size - 1.1f
        mChart.setData(data)
        mChart.setScaleEnabled(false)
        mChart.invalidate()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getArrayofDateObjects(dateArray: Array<String>): Array<LocalDate> {
        var arrayDateObjects = arrayOf<LocalDate>()
        for(j in dateArray.indices) {
            arrayDateObjects += LocalDate.of(dateArray[j].substring(6,10).toInt(), dateArray[j].substring(0,2).toInt(), dateArray[j].substring(3,5).toInt())
        }
        return arrayDateObjects
    }

    // This function converts the user's transaction history to their net worth history
    fun getNetWorthArray(amountArray: Array<Int>): IntArray {
        var netWorthArray = intArrayOf()
        var currentNetWorth = 0
        for(x in amountArray.indices) {
            currentNetWorth += amountArray[x]
            netWorthArray += currentNetWorth
        }
        return netWorthArray
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}