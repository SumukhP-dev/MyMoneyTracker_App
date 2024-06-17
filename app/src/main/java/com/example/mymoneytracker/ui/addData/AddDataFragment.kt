package com.example.mymoneytracker.ui.addData

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mymoneytracker.MainActivity
import com.example.mymoneytracker.R
import com.example.mymoneytracker.databinding.FragmentAddDataBinding


class AddDataFragment : Fragment() {
    private lateinit var viewModel: AddDataViewModel
    private var _binding: FragmentAddDataBinding? = null
    private val binding get() = _binding!!

    // one boolean variable to check whether all the text fields
    // are filled by the user, properly or not.
    private var validateFields = true

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddDataBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as MainActivity).supportActionBar?.title = ""

        viewModel = ViewModelProvider(this).get(AddDataViewModel::class.java)

        binding.addTransactionButton.setOnClickListener {
            validateFields = setAllFields()
            if (validateFields) {
                findNavController().navigate(R.id.action_addDataFragment_to_historyFragment)

                var dataList = arrayOf(
                    binding.dateText.text.toString(),
                    binding.moneyText.text.toString(),
                    binding.descriptionText.text.toString(),
                    binding.typeDropdown.selectedItem.toString()
                )

                setFragmentResult("requestKey", bundleOf("bundleKey" to dataList))

                hideKeyboard()
            }
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

        binding.dateText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.dateText.text.length == 2 || binding.dateText.text.length == 5) {
                    binding.dateText.setText("${binding.dateText.text}/")
                    binding.dateText.setSelection(binding.dateText.text.length)
                }
            }
        })
        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setAllFields(): Boolean {
        if (viewModel.checkAllFields(binding.dateText.text.toString(), binding.moneyText.text.toString())) {
            if (viewModel.dateTextErrorMessage.value?.isEmpty() == false) {
                binding.dateText.error = viewModel.dateTextErrorMessage.value
            } else if (viewModel.moneyTextErrorMessage.value?.isEmpty() == false) {
                binding.moneyText.error = viewModel.moneyTextErrorMessage.value
            }
            return false;
        }
        return true;
    }

    fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken ?: return, 0)
    }
}