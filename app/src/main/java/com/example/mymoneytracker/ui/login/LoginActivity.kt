package com.example.mymoneytracker.ui.login

import com.example.mymoneytracker.MMTApplication
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.mymoneytracker.R
import com.example.mymoneytracker.databinding.ActivityLoginBinding
import com.example.mymoneytracker.databinding.ActivityMainBinding
import com.example.mymoneytracker.databinding.FragmentHistoryBinding
import com.example.mymoneytracker.ui.home.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

open class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth

        val user = Firebase.auth.currentUser
        var emailVerified = false
        user?.let {
            // Name, email address, and profile photo Url
            val name = it.displayName
            val email = it.email
            val photoUrl = it.photoUrl

            // Check if user's email is verified
            emailVerified = it.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            val uid = it.uid
        }

        binding.signInButton.setOnClickListener {
            loginAccount(binding.email.text.toString(), binding.password.text.toString())
        }

        binding.createAccountButton.setOnClickListener {
            createAccount(binding.email.text.toString(), binding.password.text.toString())
        }
    }

    public override fun onStart() {
        super.onStart()

        var app = applicationContext as MMTApplication

        // Check if user is signed in (non-null) and update UI accordingly.
        app.currentUser = auth.currentUser

        var checkIfSignedOut = intent.getBooleanExtra("checkIfSignedOut", false)

        if (checkIfSignedOut) {
            FirebaseAuth.getInstance().signOut()
            app.currentUser = null
        }

        if (app.currentUser != null) {
            goToHomeFragment()
        }
    }

    fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("auth1", "createUserWithEmail:success")
                    val user = auth.currentUser
                    goToHomeFragment()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("auth1", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    fun loginAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("auth2", "signInWithEmail:success")
                    val user = auth.currentUser
                    goToHomeFragment()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("auth2", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    fun goToHomeFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()).commit()
        finish()
    }
}