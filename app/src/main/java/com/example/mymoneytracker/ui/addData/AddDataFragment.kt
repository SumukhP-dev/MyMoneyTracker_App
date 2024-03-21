package com.example.mymoneytracker.ui.addData

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mymoneytracker.MainActivity
import com.example.mymoneytracker.R
import com.example.mymoneytracker.databinding.FragmentAddDataBinding

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

        (activity as MainActivity).supportActionBar?.title = ""

        binding.backXButton.setOnClickListener {
            findNavController().navigate(R.id.action_addDataFragment_to_historyFragment)

            hideKeyboard()
        }

        binding.addTransactionButton.setOnClickListener {
            findNavController().navigate(R.id.action_addDataFragment_to_historyFragment)

            var dataList = arrayOf(binding.dateText.text.toString(),
                binding.moneyText.text.toString(),
                binding.descriptionText.text.toString(),
                binding.typeDropdown.selectedItem.toString())

            setFragmentResult("requestKey", bundleOf("bundleKey" to dataList))

            hideKeyboard()
        }

        val adapter = this.context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.types,
                android.R.layout.simple_spinner_item
            )
        }
        adapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        binding.typeDropdown.adapter = adapter

        return root
    }


    fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken ?: return, 0)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddDataViewModel::class.java)
    }
}