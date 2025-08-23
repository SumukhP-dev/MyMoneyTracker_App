package com.example.mymoneytracker.ui.addData

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymoneytracker.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.regex.Pattern
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class AddDataViewModel : ViewModel() {
    var dateTextErrorMessage: MutableLiveData<String> = MutableLiveData("")
    var moneyTextErrorMessage: MutableLiveData<String> = MutableLiveData("")
    var amountEntered: MutableLiveData<Double> = MutableLiveData(0.00)
    var textSizeBefore: MutableLiveData<Int> = MutableLiveData(0)
    var user: User = User.getInstance()

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

    // Adds the transaction
    @OptIn(ExperimentalUuidApi::class)
    suspend fun addTransactionToDatabase(
        date: String,
        amount: Double,
        type: String,
        description: String
    ) {
        val transactionId = Uuid.random().toString()
        val json = "{ \"transactionid\": \"${transactionId}\", \"customerid\": \"${user.getCurrentUserID()}\", \"date\": \"${date}\", \"amount\": \"${amount}\", \"type\": \"${type}\", \"description\": \"${description}\" }"

        val formBody = json
            .toRequestBody("application/json; charset=utf-8".toMediaType());

        val request = Request.Builder()
            .url("http://127.0.0.1:5000/customer_transaction")
            .post(formBody)
            .build()

        val client = OkHttpClient()

        return withContext(Dispatchers.IO) {
            try {
                val response = client.newCall(request).execute()
                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
}
