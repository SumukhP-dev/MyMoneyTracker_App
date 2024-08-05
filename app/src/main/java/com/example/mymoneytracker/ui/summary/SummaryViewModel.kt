package com.example.mymoneytracker.ui.summary

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.example.mymoneytracker.R
import com.example.mymoneytracker.model.User

class SummaryViewModel : ViewModel() {
    var user: User = User.getInstance()

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
