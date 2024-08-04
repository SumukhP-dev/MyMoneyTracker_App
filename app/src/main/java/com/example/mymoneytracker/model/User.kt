package com.example.mymoneytracker.model

import android.app.Application
import com.example.mymoneytracker.ui.history.ItemsViewModel
import com.google.firebase.auth.FirebaseUser

class User : Application() {
    companion object {
        // ArrayList of class ItemsViewModel
        var data: ArrayList<ItemsViewModel> = ArrayList<ItemsViewModel>()
        var dates: ArrayList<String> = ArrayList<String>()
        var amounts: ArrayList<Double> = ArrayList<Double>()
        var netWorthCalculated: Double = 0.0
        var user: User = User.getInstance()

        // Check if user is signed in (non-null) and update UI accordingly.
        var currentUser: FirebaseUser? = null

        var tipsMessages: String = ""
        var dataForPieChart: Array<Double> = emptyArray<Double>()

        fun getInstance(): User {
            return User()
        }
    }

    fun getData(): ArrayList<ItemsViewModel> {
        return data
    }

    fun getDates(): ArrayList<String> {
        return dates
    }

    fun getAmounts(): ArrayList<Double> {
        return amounts
    }

    fun getNetWorthCalculated(): Double {
        return netWorthCalculated
    }

    fun getCurrentUser(): FirebaseUser? {
        return currentUser
    }

    fun getTipsMessages(): String {
        return tipsMessages
    }

    fun getDataForPieChart(): Array<Double> {
        return dataForPieChart
    }

    fun addData(dataToAdd: ItemsViewModel) {
        data.add(dataToAdd)
    }

    fun addDate(dateToAdd: String) {
        dates.add(dateToAdd)
    }

    fun addAmount(amountToAdd: Double) {
        amounts.add(amountToAdd)
    }

    fun setNetWorthCalculated(netWorth: Double) {
        netWorthCalculated = netWorth
    }

    fun setCurrentUser(newUser: FirebaseUser?) {
        currentUser = newUser
    }

    fun setTipsMessages(tips: String) {
        tipsMessages = tips
    }

    fun setDataForPieChart(newDataForPieChart: Array<Double>) {
        dataForPieChart = newDataForPieChart
    }
}
