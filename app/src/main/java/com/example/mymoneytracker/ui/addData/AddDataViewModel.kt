package com.example.mymoneytracker.ui.addData

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.regex.Pattern

class AddDataViewModel : ViewModel() {
    var dateTextErrorMessage: MutableLiveData<String> = MutableLiveData("")
    var moneyTextErrorMessage: MutableLiveData<String> = MutableLiveData("")
    var amountEntered: MutableLiveData<Double> = MutableLiveData(0.00)
    var textSizeBefore: MutableLiveData<Int> = MutableLiveData(0)

    // Validates date and returns true if valid
    @RequiresApi(Build.VERSION_CODES.O)
    fun validate(registerDate: String): Boolean {
        val p: Pattern = Pattern.compile("^(1[0-9]|0[1-9]|3[0-1]|2[1-9])/(0[1-9]|1[0-2])/[0-9]{4}$")
        val m = p.matcher(registerDate)
        if (!m.matches()) return false
        val format = SimpleDateFormat("dd/MM/yyyy")
        return try {
            format.parse(registerDate)
            true
        } catch (e: ParseException) {
            false
        }
    }

    // Validates all fields of AddData Screen
    @RequiresApi(Build.VERSION_CODES.O)
    fun checkAllFields(dateText: String, moneyText: String): Boolean {
        var check = false
        if (dateText.isEmpty()) {
            dateTextErrorMessage.value = "The date is empty"
            check = true
        } else if (!validate(dateText)) {
            dateTextErrorMessage.value = "The date is improperly formatted"
            check = true
        } else if (moneyText.isEmpty()) {
            moneyTextErrorMessage.value = "The amount is empty"
            check = true
        }
        return check
    }

    // Changes money amount to be properly reflected
    // in other charts
    fun changeAmountEntered(type: String, amount: Double) {
        if (type != "Income") {
            amountEntered.value = -1 * amount
        } else {
            amountEntered.value = amount
        }
    }
}