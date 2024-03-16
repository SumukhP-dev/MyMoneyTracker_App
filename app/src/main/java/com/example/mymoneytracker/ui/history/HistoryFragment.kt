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
import com.example.mymoneytracker.R
import com.example.mymoneytracker.databinding.FragmentHistoryBinding
import kotlin.properties.Delegates


class HistoryFragment : Fragment() {

    companion object {
        fun newInstance() = HistoryFragment()
    }

    private lateinit var viewModel: HistoryViewModel
    private lateinit var inputData: Array<String>
    private lateinit var data: ArrayList<ItemsViewModel>

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private var netWorthCalculated = 0

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // ArrayList of class ItemsViewModel
        if(!this::data.isInitialized) {
            Log.d("issue6", "3")
            data = ArrayList<ItemsViewModel>()
        }

        binding.BackHistoryButton.setOnClickListener {
            findNavController().navigate(R.id.action_historyFragment_to_nav_home)
        }

        // getting the recyclerview by its id
        val recyclerview = binding.recyclerview

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(context)

        data.add(ItemsViewModel("Date", "Money", "Description"))

        setFragmentResultListener("requestKey") { requestKey, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported.
            inputData = bundle.getStringArray("bundleKey")!!
            var newDataList = arrayOf<String>()
            newDataList = newDataList.plus(inputData)
            data.add(ItemsViewModel(newDataList[0], "$" + newDataList[1], newDataList[2]))
            changeNetWorth(newDataList[1].toInt())
        }

        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

        val type = ArrayList<ItemsViewModel>().javaClass

        return root
    }

    fun changeNetWorth(valueChanged: Int) {
        netWorthCalculated += valueChanged
        Log.d("test9", netWorthCalculated.toString())
        setFragmentResult("requestKey2", bundleOf("bundleKey2" to netWorthCalculated))
        setFragmentResult("requestKey3", bundleOf("bundleKey3" to netWorthCalculated))
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