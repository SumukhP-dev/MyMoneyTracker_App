package com.example.mymoneytracker.ui.login

import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.mymoneytracker.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException

class LoginViewModel: ViewModel() {
    var user: User = User.getInstance()

    fun createAccount(email: String, password: String): Task<AuthResult> {
        val okHttpClient = OkHttpClient()

        val formBody = FormBody.Builder()
            .add(email, password)
            .build()

        val request = Request.Builder()
            .url("http://192.168.0.113:5000/debug")
            .post(formBody)
            .build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                if (response.body?.string() == "received") {

                }
            }
        })
    }

    fun loginAccount(email: String, password: String): Boolean {
        val okHttpClient = OkHttpClient()

        val formBody = FormBody.Builder()
            .add(email, password)
            .build()

        val request = Request.Builder()
            .url("http://192.168.1.8:5000/debug")
            .post(formBody)
            .build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {

            }
        })
    }
}