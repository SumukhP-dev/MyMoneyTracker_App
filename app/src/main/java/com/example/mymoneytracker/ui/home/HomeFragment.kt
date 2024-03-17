package com.example.mymoneytracker.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mymoneytracker.R
import com.example.mymoneytracker.databinding.FragmentHomeBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import kotlin.properties.Delegates


class HomeFragment : Fragment() {

    private var netWorth by Delegates.notNull<Int>()
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
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

        val data = BarData(getDataSet())
        chart.data = data
        chart.description.text = "My Chart";
        chart.animateXY(2000, 2000)
        chart.invalidate()

        return root
    }

    private fun getDataSet(): BarDataSet {
        val valueSet1 = ArrayList<BarEntry>()
        val v1e1 = BarEntry(110.000f, 0f) // Jan
        valueSet1.add(v1e1)
        val v1e2 = BarEntry(40.000f, 1f) // Feb
        valueSet1.add(v1e2)
        val v1e3 = BarEntry(60.000f, 2f) // Mar
        valueSet1.add(v1e3)
        val v1e4 = BarEntry(30.000f, 3f) // Apr
        valueSet1.add(v1e4)
        val v1e5 = BarEntry(90.000f, 4f) // May
        valueSet1.add(v1e5)
        val v1e6 = BarEntry(100.000f, 5f) // Jun
        valueSet1.add(v1e6)
        val valueSet2 = ArrayList<BarEntry>()
        val v2e1 = BarEntry(150.000f, 0f) // Jan
        valueSet2.add(v2e1)
        val v2e2 = BarEntry(90.000f, 1f) // Feb
        valueSet2.add(v2e2)
        val v2e3 = BarEntry(120.000f, 2f) // Mar
        valueSet2.add(v2e3)
        val v2e4 = BarEntry(60.000f, 3f) // Apr
        valueSet2.add(v2e4)
        val v2e5 = BarEntry(20.000f, 4f) // May
        valueSet2.add(v2e5)
        val v2e6 = BarEntry(80.000f, 5f) // Jun
        valueSet2.add(v2e6)
        val barDataSet1 = BarDataSet(valueSet1, "Brand 1")
        barDataSet1.color = Color.rgb(0, 155, 0)
        val barDataSet2 = BarDataSet(valueSet2, "Brand 2")
        barDataSet2.setColors(*ColorTemplate.COLORFUL_COLORS)
        var dataSets: BarDataSet = BarDataSet(valueSet1, "Data")
        return dataSets
    }

    private fun getXAxisValues(): ArrayList<String> {
        val xAxis = ArrayList<String>()
        xAxis.add("JAN")
        xAxis.add("FEB")
        xAxis.add("MAR")
        xAxis.add("APR")
        xAxis.add("MAY")
        xAxis.add("JUN")
        return xAxis
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}