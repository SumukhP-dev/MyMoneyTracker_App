package com.example.mymoneytracker.ui.history

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModel
import com.example.mymoneytracker.model.User
import org.checkerframework.checker.units.qual.A

class HistoryViewModel : ViewModel() {
    fun sendDataToPieChart(newDataList: Array<String>, dataForPieChart: Array<Int>): Array<Int> {
        when (newDataList[3]) {
            "Rent/Mortgage" -> { dataForPieChart[0] += newDataList[1].toInt() }
            "Utilities" -> { dataForPieChart[1] += newDataList[1].toInt() }
            "Student Loans" -> { dataForPieChart[2] += newDataList[1].toInt() }
            "Car payments" -> { dataForPieChart[3] += newDataList[1].toInt() }
            "Food" -> { dataForPieChart[4] += newDataList[1].toInt() }
            "Fun" -> { dataForPieChart[5] += newDataList[1].toInt() }
            "Miscellaneous" -> { dataForPieChart[6] += newDataList[1].toInt() }
        }
        return dataForPieChart
    }

    fun changeNetWorth(valueChanged: Int) {
        //User.getInstance().getNetWorthCalculated() += valueChanged
    }

    fun addData (newDataList: Array<String>) {
        if (newDataList[1].toInt() >= 0) {
            User.getInstance().getData().add(ItemsViewModel(newDataList[0], "$" + newDataList[1], newDataList[2]))
        } else {
            val endIndex = newDataList[1].length - 1
            User.getInstance().getData().add(ItemsViewModel(newDataList[0], newDataList[1].slice(listOf(0)) + "$" + newDataList[1].slice(1..endIndex), newDataList[2]))
        }
        //User.dates.add(newDataList[0])
        //User.amounts.add(newDataList[1].toInt())
    }
}