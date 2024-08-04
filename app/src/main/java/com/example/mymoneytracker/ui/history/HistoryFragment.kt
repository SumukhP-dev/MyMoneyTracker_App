package com.example.mymoneytracker.ui.history

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymoneytracker.MainActivity
import com.example.mymoneytracker.R
import com.example.mymoneytracker.databinding.FragmentHistoryBinding
import com.example.mymoneytracker.ui.OnSwipeTouchListener

class HistoryFragment : Fragment() {
    private lateinit var viewModel: HistoryViewModel
    private lateinit var inputData: Array<String>
    private lateinit var dataForPieChart: Array<Double>
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val TAG = "testCalls"

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)

        // This implements swipe gestures to go to Home Fragment
        binding.constraintLayout4.setOnTouchListener(object : OnSwipeTouchListener(context) {
            override fun onSwipeRight() {
            }
            override fun onSwipeLeft() {
                findNavController().navigate(R.id.action_historyFragment_to_nav_home)
            }
        })

        (activity as MainActivity).supportActionBar?.title = "History"

        if (!this::dataForPieChart.isInitialized) {
            dataForPieChart = arrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        }

        binding.backHistoryButton.setOnClickListener {
            findNavController().navigate(R.id.action_historyFragment_to_nav_home)
        }

        binding.addFAB.setOnClickListener {
            findNavController().navigate(R.id.action_historyFragment_to_addDataFragment)
        }

        // Getting the recyclerview by its id
        val recyclerview = binding.recyclerView

        // This creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(context)

        if (viewModel.user.getData().size == 0) {
            viewModel.user.getData().add(ItemsViewModel("Date", "Money", "Description"))
        }

        setFragmentResultListener("dataListKey") { requestKey, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported.
            inputData = bundle.getStringArray("dataListBundleKey")!!
            var newDataList = arrayOf<String>()
            newDataList = newDataList.plus(inputData)
            viewModel.addData(newDataList)
            viewModel.changeNetWorth(newDataList[1].toDouble())
            val dataForPieChart2 = viewModel.sendDataToPieChart(newDataList, dataForPieChart)
            viewModel.user.setDataForPieChart(dataForPieChart2)
        }

        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(viewModel.user.getData())

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

        return root
    }
}
