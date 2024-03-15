package com.example.mymoneytracker.ui.summary

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.mymoneytracker.R
import com.example.mymoneytracker.databinding.FragmentHistoryBinding
import com.example.mymoneytracker.databinding.FragmentSummaryBinding
import kotlin.properties.Delegates

class SummaryFragment : Fragment() {

    companion object {
        fun newInstance() = SummaryFragment()
    }

    private var netWorth by Delegates.notNull<Int>()
    private lateinit var viewModel: SummaryViewModel
    private var _binding: FragmentSummaryBinding? = null
    private val binding get() = _binding!!

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
            Log.d("issue1", "path1")
        }

        return root
    }

    private fun displayTips(netWorth: Int) {
        Log.d("issue1", "path2")
        if(netWorth > 100000) {
            binding.tipsText.text = getString(R.string.tipBase,
                resources.getString(R.string.tip1),
                resources.getString(R.string.tip6),
                resources.getString(R.string.tip7),
                resources.getString(R.string.tip8),
                resources.getString(R.string.tip9))
        } else if (netWorth > 10000) {
            binding.tipsText.text = getString(R.string.tipBase,
                resources.getString(R.string.tip2),
                resources.getString(R.string.tip6),
                resources.getString(R.string.tip7),
                resources.getString(R.string.tip8),
                resources.getString(R.string.tip9))
        } else if (netWorth > 1000) {
            binding.tipsText.text = getString(R.string.tipBase,
                resources.getString(R.string.tip3),
                resources.getString(R.string.tip6),
                resources.getString(R.string.tip7),
                resources.getString(R.string.tip8),
                resources.getString(R.string.tip9))
        } else if (netWorth > -1000) {
            binding.tipsText.text = getString(R.string.tipBase,
                resources.getString(R.string.tip4),
                resources.getString(R.string.tip6),
                resources.getString(R.string.tip7),
                resources.getString(R.string.tip8),
                resources.getString(R.string.tip9))
        } else if (netWorth > -10000) {
            binding.tipsText.text = getString(R.string.tipBase,
                resources.getString(R.string.tip5),
                resources.getString(R.string.tip6),
                resources.getString(R.string.tip7),
                resources.getString(R.string.tip8),
                resources.getString(R.string.tip9))
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SummaryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}