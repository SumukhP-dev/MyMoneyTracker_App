package com.example.mymoneytracker.ui.login

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymoneytracker.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel: ViewModel() {
    var user: User = User.getInstance()

    fun createAccount(email: String, password: String,
                      auth: FirebaseAuth): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun loginAccount(email: String, password: String,
                     auth: FirebaseAuth): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }
}