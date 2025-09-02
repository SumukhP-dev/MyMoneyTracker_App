package com.example.mymoneytracker.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mymoneytracker.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


class LoginViewModel: ViewModel() {
    var user: User = User.getInstance()
    val TAG = "Authentication"

    @OptIn(ExperimentalUuidApi::class)
    suspend fun createAccount(email: String, password: String): Boolean {
        val userId = Uuid.random().toString()

        val json = "{ \"customerid\": \"$userId\", \"customername\": \"$email\", \"customerpassword\": \"$password\" }"

        val formBody = json
            .toRequestBody("application/json; charset=utf-8".toMediaType());

        val request = Request.Builder()
            .url("http://127.0.0.1:5000/customer")
            .post(formBody)
            .build()

        val client = OkHttpClient()

        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, " request: $request")

                val response = client.newCall(request).execute()
                Log.d(TAG, " response: $response")

                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    suspend fun loginAccount(email: String, password: String): Boolean {
        val request = Request.Builder()
            .url("http://127.0.0.1:5000/customer/$email/$password")
            .get()
            .build()

        val client = OkHttpClient()

        val response: String = withContext(Dispatchers.IO) {
            try {
                val response = client.newCall(request).execute()
                response.body?.string() ?: ""
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }

        return response != ""
    }
}