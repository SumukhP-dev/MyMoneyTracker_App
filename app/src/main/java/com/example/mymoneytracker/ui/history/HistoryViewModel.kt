package com.example.mymoneytracker.ui.history

import androidx.lifecycle.ViewModel
import com.example.mymoneytracker.model.User
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException

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

    fun addData(newDataList: Array<String>) {
        user.addDate(newDataList[0])
        user.addAmount(newDataList[1].toDouble())
    }

    fun getTransactions() {
        val request = Request.Builder()
            .url("http://127.0.0.1:5000/customer_transaction/${user.getCurrentUserID()}")
            .get()
            .build()

        val client = OkHttpClient()

        try {
            val response = client.newCall(request).execute()

            if (!response.isSuccessful) {
                return
            }

            // Check if the response body is null
            val responseBodyString = response.body?.string()
            if (responseBodyString == null) {
                println("Error: Response body is empty")
                return
            }

            val dataArrays: Array<Array<String>> = Json.decodeFromString(responseBodyString)

            for (dataArray in dataArrays) {
                var newDataList = arrayOf<String>()
                newDataList = newDataList.plus(dataArray)
                addData(newDataList)
                changeNetWorth(newDataList[1].toDouble())
                val dataForPieChart2 = sendDataToPieChart(newDataList, user.getDataForPieChart())
                user.setDataForPieChart(dataForPieChart2)
            }
        } catch (e: Exception) {
            // Handle JSON parsing exceptions
            e.printStackTrace()
        }
    }
}