package com.example.mymoneytracker.ui.summary

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymoneytracker.R
import com.example.mymoneytracker.model.User

class SummaryViewModel: ViewModel() {
    var tipsMessages: MutableLiveData<String> = MutableLiveData("")

    // This function displays tips based on net worth
    fun displayTips(netWorth: Int) {
        if(netWorth >= 100000) {
            tipsMessages.value = UiText.StringResources(
                resId = R.string.tipBase,
                R.string.tip1,
                R.string.tip6,
                R.string.tip7,
                R.string.tip8,
                R.string.tip9).toString()
        } else if (netWorth >= 10000) {
            tipsMessages.value = UiText.StringResources(
                resId = R.string.tipBase,
                R.string.tip2,
                R.string.tip6,
                R.string.tip7,
                R.string.tip8,
                R.string.tip9).toString()
        } else if (netWorth >= 1000) {
            tipsMessages.value = UiText.StringResources(
                resId = R.string.tipBase,
                R.string.tip3,
                R.string.tip6,
                R.string.tip7,
                R.string.tip8,
                R.string.tip9).toString()
        } else if (netWorth >= -1000) {
            tipsMessages.value = UiText.StringResources(
                resId = R.string.tipBase,
                R.string.tip4,
                R.string.tip6,
                R.string.tip7,
                R.string.tip8,
                R.string.tip9).toString()
        } else {
            tipsMessages.value = UiText.StringResources(
                resId = R.string.tipBase,
                R.string.tip5,
                R.string.tip6,
                R.string.tip7,
                R.string.tip8,
                R.string.tip9).toString()
        }
    }

    // This function gets the amount of progress for the progress bar
    fun getProgress(netWorth: Int): Int {
        return if (netWorth >= 100000) {
            100
        } else if (netWorth >= 10000) {
            80
        } else if (netWorth >= 1000) {
            60
        } else if (netWorth >= -1000) {
            40
        } else {
            20
        }
    }
}