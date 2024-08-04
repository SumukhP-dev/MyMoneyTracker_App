package com.example.mymoneytracker.ui.summary

import androidx.lifecycle.ViewModel
import com.example.mymoneytracker.R
import com.example.mymoneytracker.model.User

class SummaryViewModel : ViewModel() {
    var user: User = User.getInstance()

    // This function displays tips based on net worth
    fun displayTips(netWorth: Double) {
        if (netWorth >= 100000) {
            user.setTipsMessages(UiText.StringResources(
                resId = R.string.tipBase,
                R.string.tip1,
                R.string.tip6,
                R.string.tip7,
                R.string.tip8,
                R.string.tip9
            ).toString())
        } else if (netWorth >= 10000) {
            user.setTipsMessages(UiText.StringResources(
                resId = R.string.tipBase,
                R.string.tip2,
                R.string.tip6,
                R.string.tip7,
                R.string.tip8,
                R.string.tip9
            ).toString())
        } else if (netWorth >= 1000) {
            user.setTipsMessages(UiText.StringResources(
                resId = R.string.tipBase,
                R.string.tip3,
                R.string.tip6,
                R.string.tip7,
                R.string.tip8,
                R.string.tip9
            ).toString())
        } else if (netWorth >= -1000) {
            user.setTipsMessages(UiText.StringResources(
                resId = R.string.tipBase,
                R.string.tip4,
                R.string.tip6,
                R.string.tip7,
                R.string.tip8,
                R.string.tip9
            ).toString())
        } else {
            user.setTipsMessages(UiText.StringResources(
                resId = R.string.tipBase,
                R.string.tip5,
                R.string.tip6,
                R.string.tip7,
                R.string.tip8,
                R.string.tip9
            ).toString())
        }
    }

    // This function gets the amount of progress for the progress bar
    fun getProgress(netWorth: Double): Int {
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
