package com.example.mymoneytracker.ui.addData

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mymoneytracker.R
import com.example.mymoneytracker.databinding.FragmentAddDataBinding
import com.example.mymoneytracker.ui.history.HistoryFragment

class AddDataFragment : Fragment() {

    companion object {
        fun newInstance() = AddDataFragment()
    }

    private lateinit var viewModel: AddDataViewModel
    private var _binding: FragmentAddDataBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddDataBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.BackXButton.setOnClickListener {
            findNavController().navigate(R.id.action_addDataFragment_to_historyFragment)
        }

        binding.addTransactionButton.setOnClickListener {
            findNavController().navigate(R.id.action_addDataFragment_to_historyFragment)

            var dataList = arrayOf<String>(binding.dateText.toString(), binding.moneyText.toString(), binding.descriptionText.toString())

            val bundle = Bundle()
            bundle.putStringArray("data", dataList)
            HistoryFragment().arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.frameLayout, HistoryFragment())?.commit()
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddDataViewModel::class.java)
        // TODO: Use the ViewModel
    }

}