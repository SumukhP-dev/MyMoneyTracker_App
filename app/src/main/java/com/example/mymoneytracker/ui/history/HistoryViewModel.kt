package com.example.mymoneytracker.ui.history

import androidx.lifecycle.ViewModel
import com.example.mymoneytracker.model.User

class HistoryViewModel : ViewModel() {
    var user: User = User.getInstance()

    fun sendDataToPieChart(newDataList: Array<String>, dataForPieChart: Array<Double>): Array<Double> {
        when (newDataList[3]) {
            "Rent/Mortgage" -> { dataForPieChart[0] += newDataList[1].toDouble() }
            "Utilities" -> { dataForPieChart[1] += newDataList[1].toDouble() }
            "Student Loans" -> { dataForPieChart[2] += newDataList[1].toDouble() }
            "Car payments" -> { dataForPieChart[3] += newDataList[1].toDouble() }
            "Food" -> { dataForPieChart[4] += newDataList[1].toDouble() }
            "Fun" -> { dataForPieChart[5] += newDataList[1].toDouble() }
            "Miscellaneous" -> { dataForPieChart[6] += newDataList[1].toDouble() }
        }
        return dataForPieChart
    }

    fun changeNetWorth(valueChanged: Double) {
        user.setNetWorthCalculated(user.getNetWorthCalculated() + valueChanged)
    }

    fun addData (newDataList: Array<String>) {
        if (newDataList[1].toDouble() >= 0) {
            user.addData(ItemsViewModel(newDataList[0], "$" + newDataList[1], newDataList[2]))
        } else {
            val endIndex = newDataList[1].length - 1
            user.addData(ItemsViewModel(newDataList[0], newDataList[1].slice(listOf(0))
                    + "$" + newDataList[1].slice(1..endIndex), newDataList[2]))
        }
        user.addDate(newDataList[0])
        user.addAmount(newDataList[1].toDouble())
    }
}