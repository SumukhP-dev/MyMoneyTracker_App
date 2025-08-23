package com.example.mymoneytracker.model

import android.app.Application
import com.example.mymoneytracker.UserDataModel
import com.example.mymoneytracker.ui.history.ItemsViewModel
import com.google.firebase.auth.FirebaseUser

class User : Application() {
    companion object {
        // ArrayList of class ItemsViewModel
        var dates: ArrayList<String> = ArrayList<String>()
        var amounts: ArrayList<Double> = ArrayList<Double>()
        var netWorthCalculated: Double = 0.0
        var user: User = getInstance()
        var data: ArrayList<ItemsViewModel> = ArrayList<ItemsViewModel>()
        var userData: ArrayList<UserDataModel> = ArrayList<UserDataModel>()

        // Check if user is signed in and update UI accordingly.
        var currentUserLoggedIn: Boolean = false
        var currentUserID: String = ""

        var tipsMessages: String = ""
        var dataForPieChart: Array<Double> = arrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)

        fun getInstance(): User {
            return User()
        }
    }

    fun getData(): ArrayList<ItemsViewModel> {
        return data
    }

    fun getUserData(): ArrayList<UserDataModel> {
        return userData
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

    fun getCurrentUserLoggedIn(): Boolean {
        return currentUserLoggedIn
    }

    fun getCurrentUserID(): String {
        return currentUserID
    }

    fun getTipsMessages(): String {
        return tipsMessages
    }

    fun getDataForPieChart(): Array<Double> {
        return dataForPieChart
    }

    fun addDate(dateToAdd: String) {
        dates.add(dateToAdd)
    }

    fun addAmount(amountToAdd: Double) {
        amounts.add(amountToAdd)
    }

    fun addToData(dataElement: ItemsViewModel) {
        data.add(dataElement)
    }

    fun setUserData(userDataList: ArrayList<UserDataModel>) {
        userData = userDataList
    }

    fun setNetWorthCalculated(netWorth: Double) {
        netWorthCalculated = netWorth
    }

    fun setCurrentUserLoggedIn(currentUserLoggedInCheck: Boolean) {
        currentUserLoggedIn = currentUserLoggedInCheck
    }

    fun setCurrentUserID(userID: String) {
        currentUserID = userID
    }

    fun setTipsMessages(tips: String) {
        tipsMessages = tips
    }

    fun setDataForPieChart(newDataForPieChart: Array<Double>) {
        dataForPieChart = newDataForPieChart
    }
}
