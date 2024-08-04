package com.example.mymoneytracker.ui.home

import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymoneytracker.model.User
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import java.time.LocalDate
import java.util.TreeMap

class HomeViewModel : ViewModel() {
    var user: User = User.getInstance()
    var netWorthAmountColor: MutableLiveData<Int> = MutableLiveData(0)
    var netWorthAmountText: MutableLiveData<String> = MutableLiveData("")

    // This function creates a bar chart to show net worth over a period of time
    fun barChart(treeMapToAdd: TreeMap<LocalDate, Double>, chart: BarChart) {
        var mChart: BarChart = chart
        mChart.setDrawBarShadow(false)
        mChart.getDescription().setEnabled(false)
        mChart.setPinchZoom(false)
        mChart.setDrawGridBackground(true)

        var labels = emptyArray<String>()
        treeMapToAdd.navigableKeySet().forEach { label ->
            labels += label.toString()
        }
        labels += ""

        // The xAxis variable defines the x-axis values of the chart
        val xAxis: XAxis = mChart.getXAxis()
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(true)
        xAxis.granularity = 1f
        xAxis.textColor = Color.BLACK
        xAxis.textSize = 12f
        xAxis.axisLineColor = Color.WHITE
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)

        // The leftAxis variable defines the y-axis values of the chart
        val leftAxis: YAxis = mChart.getAxisLeft()
        leftAxis.textColor = Color.BLACK
        leftAxis.textSize = 12f
        leftAxis.axisLineColor = Color.WHITE
        leftAxis.setDrawGridLines(true)
        leftAxis.granularity = 2f
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        mChart.getAxisRight().setEnabled(false)
        mChart.getLegend().setEnabled(false)
        val valOne = treeMapToAdd.values.toList()
        var barOne = mutableListOf<BarEntry>()
        val dataSets = ArrayList<IBarDataSet>()
        for (i in valOne.indices) {
            barOne += (BarEntry(i.toFloat(), valOne[i].toFloat()))
            val set = BarDataSet(barOne.toMutableList(), "barOne")

            // This if check determines if the bar should be green or red
            if (valOne[i].toFloat() >= 0) {
                set.color = Color.GREEN
            } else {
                set.color = Color.RED
            }
            set.isHighlightEnabled = false
            set.setDrawValues(false)
            dataSets.add(set)
            barOne = mutableListOf<BarEntry>()
        }
        val data = BarData(dataSets)
        val barWidth = 0.3f
        data.barWidth = barWidth
        xAxis.axisMaximum = labels.size - 1.1f
        mChart.setData(data)
        mChart.setScaleEnabled(false)
        mChart.invalidate()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getArrayofDateObjects(dateArray: ArrayList<String>): ArrayList<LocalDate> {
        var arrayDateObjects = ArrayList<LocalDate>()
        for (j in dateArray.indices) {
            arrayDateObjects.add(
                LocalDate.of(
                    dateArray[j].substring(6, 10).toInt(),
                    dateArray[j].substring(0, 2).toInt(),
                    dateArray[j].substring(3, 5).toInt()
                )
            )
        }
        return arrayDateObjects
    }

    // This function converts the user's transaction history to their net worth history
    fun getNetWorthArray(amountArray: ArrayList<Double>): ArrayList<Double> {
        var netWorthArray = ArrayList<Double>()
        var currentNetWorth = 0.00
        for (x in amountArray.indices) {
            currentNetWorth += amountArray[x]
            netWorthArray.add(currentNetWorth)
        }
        return netWorthArray
    }

    fun netWorthColorChange(netWorth: Double) {
        val formattedMoneyText = String.format("%.2f", netWorth)
        if (netWorth >= 0) {
            netWorthAmountColor.value = Color.parseColor("#00FF0A")
            netWorthAmountText.value = "$$formattedMoneyText"
        } else {
            netWorthAmountColor.value = Color.parseColor("#FF1100")
            netWorthAmountText.value = "($$formattedMoneyText)"
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createBarChart(): TreeMap<LocalDate, Double> {
        val netWorthArrayList = getNetWorthArray(user.getAmounts())
        val dateArrayList = getArrayofDateObjects(user.getDates())

        // Adds dates and net worth information to tree
        // map which automatically sorts them
        var treeMap = TreeMap<LocalDate, Double>()
        for (i in netWorthArrayList.indices) {
            treeMap.put(dateArrayList[i], netWorthArrayList[i])
        }
        return treeMap
    }
}
