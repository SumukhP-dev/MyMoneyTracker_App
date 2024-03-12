package com.example.mymoneytracker.ui.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoneytracker.R
import com.example.mymoneytracker.databinding.ActivityMainBinding
import com.example.mymoneytracker.databinding.FragmentHomeBinding
import com.example.mymoneytracker.ui.home.HomeViewModel
import java.security.AccessController.getContext
import com.example.mymoneytracker.databinding.FragmentHistoryBinding
import java.util.Arrays


class HistoryFragment : Fragment() {

    companion object {
        fun newInstance() = HistoryFragment()
    }

    private lateinit var viewModel: HistoryViewModel
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private var dataList = emptyArray<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.BackHistoryButton.setOnClickListener {
            findNavController().navigate(R.id.action_historyFragment_to_nav_home)
        }

        val args = this.arguments
        Log.d("data0", args.toString())
        if(args != null) {
            val inputData: Array<String>? = args?.get("data") as Array<String>
            Log.d("data1", Arrays.toString(inputData))

            for (counter in 0..2) {
                if(inputData != null) {
                    dataList += inputData[counter]
                }
            }
        }


        // getting the recyclerview by its id
        val recyclerview = binding.recyclerview

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(context)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()
        var counterInc = 0

        for(counter in 1..(dataList.size/3)) {
            data.add(ItemsViewModel(dataList[counter+counterInc], dataList[counter+counterInc], dataList[counter+counterInc]))
            counterInc += 3
        }
        Log.d("data1", data.toString())


        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter


        return root
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