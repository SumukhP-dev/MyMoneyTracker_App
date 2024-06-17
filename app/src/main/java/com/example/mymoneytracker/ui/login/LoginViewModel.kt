package com.example.mymoneytracker.ui.login

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel: ViewModel() {
    var transitionToMainActivity: MutableLiveData<Boolean> = MutableLiveData(false)

    fun createAccount(email: String, password: String, auth: FirebaseAuth,
                      appCompatActivity: AppCompatActivity) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(appCompatActivity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("auth1", "createUserWithEmail:success")
                    val user = auth.currentUser
                    transitionToMainActivity.value = true
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("auth1", "createUserWithEmail:failure", task.exception)
                    transitionToMainActivity.value = false
                }
            }
    }

    fun loginAccount(email: String, password: String, auth: FirebaseAuth,
                     appCompatActivity: AppCompatActivity) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(appCompatActivity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("auth2", "signInWithEmail:success")
                    val user = auth.currentUser
                    transitionToMainActivity.value = true
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("auth2", "signInWithEmail:failure", task.exception)
                    transitionToMainActivity.value = false
                }
            }
    }
}