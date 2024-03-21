package com.example.mymoneytracker.ui.history

import android.R.string
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.google.common.primitives.Ints


class HistoryFragment : Fragment() {

    companion object {
        fun newInstance() = HistoryFragment()
    }

    private lateinit var viewModel: HistoryViewModel
    private lateinit var inputData: Array<String>
    private lateinit var data: ArrayList<ItemsViewModel>
    private lateinit var dataForPieChart: Array<Int>


    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private var netWorthCalculated = 0
    private val TAG = "testCalls"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called with: savedInstanceState = $savedInstanceState")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as MainActivity).supportActionBar?.title = "History"

        // ArrayList of class ItemsViewModel
        if(!this::data.isInitialized) {
            Log.d("issue6", "3")
            data = ArrayList<ItemsViewModel>()
        }

        if(!this::dataForPieChart.isInitialized) {
            dataForPieChart = arrayOf(0, 0, 0, 0, 0, 0, 0)
        }

        binding.backHistoryButton.setOnClickListener {
            findNavController().navigate(R.id.action_historyFragment_to_nav_home)
        }

        // Getting the recyclerview by its id
        val recyclerview = binding.recyclerView

        // This creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(context)

        data.add(ItemsViewModel("Date", "Money", "Description"))

        setFragmentResultListener("requestKey") { requestKey, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported.
            inputData = bundle.getStringArray("bundleKey")!!
            var newDataList = arrayOf<String>()
            newDataList = newDataList.plus(inputData)
            addToArrayListItemsViewModel(newDataList)
            changeNetWorth(newDataList[1].toInt())
            sendDataToPieChart(newDataList)
        }

        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

        val type = ArrayList<ItemsViewModel>().javaClass

        return root
    }

    override fun onSaveInstanceState(bundle: Bundle) {
        super.onSaveInstanceState(bundle)
        Log.d(TAG, "onSaveInstanceState() called with: bundle = $bundle")
    }

    fun changeNetWorth(valueChanged: Int) {
        netWorthCalculated += valueChanged
        setFragmentResult("requestKey2", bundleOf("bundleKey2" to netWorthCalculated))
        setFragmentResult("requestKey3", bundleOf("bundleKey3" to netWorthCalculated))
    }

    fun addToArrayListItemsViewModel (newDataList: Array<String>) {
        data.add(ItemsViewModel(newDataList[0], "$" + newDataList[1], newDataList[2]))
    }

    fun sendDataToPieChart(newDataList: Array<String>) {
        when (newDataList[3]) {
            "Rent/Mortgage" -> { dataForPieChart[0] += newDataList[1].toInt() }
            "Utilities" -> { dataForPieChart[1] += newDataList[1].toInt() }
            "Student Loans" -> { dataForPieChart[2] += newDataList[1].toInt() }
            "Car payments" -> { dataForPieChart[3] += newDataList[1].toInt() }
            "Food" -> { dataForPieChart[4] += newDataList[1].toInt() }
            "Fun" -> { dataForPieChart[5] += newDataList[1].toInt() }
            "Miscellaneous" -> { dataForPieChart[6] += newDataList[1].toInt() }
        }
        setFragmentResult("requestKey4", bundleOf("bundleKey4" to dataForPieChart))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // FAB to add transactions fragment
        val fab: View = view.findViewById(R.id.addFAB)
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_historyFragment_to_addDataFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        // TODO: Use the ViewModel
    }
}