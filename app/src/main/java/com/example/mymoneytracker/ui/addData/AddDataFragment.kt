package com.example.mymoneytracker.ui.addData

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.regex.Pattern


class AddDataFragment : Fragment() {

    companion object {
        fun newInstance() = AddDataFragment()
    }

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

        binding.addTransactionButton.setOnClickListener {
            validateFields = checkAllFields()
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

        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun validate(registerDate: String): Boolean {
        val p: Pattern = Pattern.compile("^(1[0-9]|0[1-9]|3[0-1]|2[1-9])/(0[1-9]|1[0-2])/[0-9]{4}$")
        val m = p.matcher(registerDate)
        if (!m.matches()) return false
        val format = SimpleDateFormat("dd/MM/yyyy")
        return try {
            format.parse(registerDate)
            true
        } catch (e: ParseException) {
            false
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkAllFields(): Boolean {
        var check = true
        if (binding.dateText.text.isEmpty()) {
            binding.dateText.error = "The date is empty"
            check = false
        } else if (!validate(binding.dateText.text.toString())) {
            binding.dateText.error = "The date is improperly formatted"
            check = false
        } else if (binding.moneyText.text.toString().isEmpty()) {
            binding.moneyText.error = "The amount is empty"
            check = false
        }
        return check
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