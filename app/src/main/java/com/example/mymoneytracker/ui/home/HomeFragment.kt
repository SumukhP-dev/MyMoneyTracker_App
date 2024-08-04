package com.example.mymoneytracker.ui.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mymoneytracker.R
import com.example.mymoneytracker.databinding.FragmentHomeBinding
import com.example.mymoneytracker.ui.OnSwipeTouchListener
import java.text.DecimalFormat

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var viewModel: HomeViewModel
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // This implements swipe gestures to go to History Fragment and Summary Fragment
        binding.nestedScrollView2.setOnTouchListener(object: OnSwipeTouchListener(context) {
            override fun onSwipeRight() {
                findNavController().navigate(R.id.action_nav_home_to_historyFragment)
            }
            override fun onSwipeLeft() {
                findNavController().navigate(R.id.action_nav_home_to_summaryFragment)
            }
        })

        binding.historyButton.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_historyFragment)
        }

        binding.summaryButton.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_summaryFragment)
        }

        binding.netWorthAmount.text = viewModel.user.getNetWorthCalculated().toString()
        viewModel.netWorthColorChange(viewModel.user.getNetWorthCalculated())
        viewModel.netWorthAmountColor.value?.let { binding.netWorthAmount.setTextColor(it) }

        binding.netWorthAmount.text = viewModel.netWorthAmountText.value.toString()


        val treeMap = viewModel.createBarChart()
        viewModel.barChart(treeMap, binding.chart)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}