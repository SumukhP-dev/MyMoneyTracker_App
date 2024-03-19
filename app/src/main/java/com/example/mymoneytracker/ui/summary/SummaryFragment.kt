package com.example.mymoneytracker.ui.summary

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mymoneytracker.R
import com.example.mymoneytracker.databinding.FragmentSummaryBinding
import org.eazegraph.lib.models.PieModel
import kotlin.properties.Delegates


class SummaryFragment : Fragment() {

    companion object {
        fun newInstance() = SummaryFragment()
    }

    private lateinit var viewModel: SummaryViewModel
    private lateinit var dataForPieChart: Array<Int>

    private var _binding: FragmentSummaryBinding? = null
    private val binding get() = _binding!!
    private var netWorth by Delegates.notNull<Int>()
    private var checkIfPieChartEmpty = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSummaryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.BackSummaryButton.setOnClickListener {
            findNavController().navigate(R.id.action_summaryFragment_to_nav_home)
        }

        setFragmentResultListener("requestKey3") { requestKey, bundle ->
            netWorth = bundle.getInt("bundleKey3")
            displayTips(netWorth)
            setProgress(netWorth)
        }

        setFragmentResultListener("requestKey4") { requestKey, bundle ->
            dataForPieChart = bundle.getSerializable("bundleKey4") as Array<Int>
            setPieChart(dataForPieChart)
            checkIfPieChartEmpty = false
        }
        if (checkIfPieChartEmpty) {
            setPieChartDefault()
        }

        binding.progressBar.max = 100
        binding.progressBar.progressTintList = ColorStateList.valueOf(Color.GREEN);

        return root
    }

    // This function displays tips based on net worth
    private fun displayTips(netWorth: Int) {
        if(netWorth >= 100000) {
            binding.tipsText.text = getString(R.string.tipBase,
                resources.getString(R.string.tip1),
                resources.getString(R.string.tip6),
                resources.getString(R.string.tip7),
                resources.getString(R.string.tip8),
                resources.getString(R.string.tip9))
        } else if (netWorth >= 10000) {
            binding.tipsText.text = getString(R.string.tipBase,
                resources.getString(R.string.tip2),
                resources.getString(R.string.tip6),
                resources.getString(R.string.tip7),
                resources.getString(R.string.tip8),
                resources.getString(R.string.tip9))
        } else if (netWorth >= 1000) {
            binding.tipsText.text = getString(R.string.tipBase,
                resources.getString(R.string.tip3),
                resources.getString(R.string.tip6),
                resources.getString(R.string.tip7),
                resources.getString(R.string.tip8),
                resources.getString(R.string.tip9))
        } else if (netWorth >= -1000) {
            binding.tipsText.text = getString(R.string.tipBase,
                resources.getString(R.string.tip4),
                resources.getString(R.string.tip6),
                resources.getString(R.string.tip7),
                resources.getString(R.string.tip8),
                resources.getString(R.string.tip9))
        } else {
            binding.tipsText.text = getString(R.string.tipBase,
                resources.getString(R.string.tip5),
                resources.getString(R.string.tip6),
                resources.getString(R.string.tip7),
                resources.getString(R.string.tip8),
                resources.getString(R.string.tip9))
        }
    }

    // This function sets the amount of progress for the progress bar
    private fun setProgress(netWorth: Int) {
        binding.progressBar.progress = getProgress(netWorth)
    }


    // This function gets the amount of progress for the progress bar
    private fun getProgress(netWorth: Int): Int {
        if(netWorth >= 100000) {
            return 100
        } else if (netWorth >= 10000) {
            return 80
        } else if (netWorth >= 1000) {
            return 60
        } else if (netWorth >= -1000) {
            return 40
        } else {
            return 20
        }
    }

    fun setPieChartDefault(){
        binding.pieChart.addPieSlice(
            PieModel(
                100F, Color.parseColor("#A9A9A9")
            )
        )
    }

    fun setPieChart(dataForPieChart: Array<Int>) {
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SummaryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}