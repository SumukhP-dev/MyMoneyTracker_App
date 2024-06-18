package com.example.mymoneytracker.ui.login

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mymoneytracker.R
import com.example.mymoneytracker.databinding.ActivityLoginBinding
import com.example.mymoneytracker.ui.home.HomeFragment
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

open class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

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
            val taskResult: Task<AuthResult> =
                viewModel.loginAccount(binding.email.text.toString(),
                binding.password.text.toString(), auth)
            taskResult.addOnCompleteListener(this) { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    // Sign in success, update UI
                    Log.d("auth", "signInWithEmail:success")
                    val user = auth.currentUser
                    goToHomeFragment()
                } else {
                    // If sign in fails, display a message to the user
                    Log.w("auth", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        }

        binding.createAccountButton.setOnClickListener {
            val taskResult: Task<AuthResult> = viewModel.createAccount(
                binding.email.text.toString(),
                binding.password.text.toString(), auth)
            taskResult.addOnCompleteListener(this) { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        // Sign up success, update UI
                        Log.d("auth2", "signUpWithEmail:success")
                        val user = auth.currentUser
                        goToHomeFragment()
                    } else {
                        // If sign up fails, display a message to the user
                        Log.w("auth2", "signUpWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
            }
        }
    }

    public override fun onStart() {
        super.onStart()

        // Check if user is signed in (non-null) and update UI accordingly.
        viewModel.user.setCurrentUser(auth.currentUser)

        var checkIfSignedOut = intent.getBooleanExtra("checkIfSignedOut", false)

        if (checkIfSignedOut) {
            FirebaseAuth.getInstance().signOut()
            viewModel.user.setCurrentUser(null)
        }

        if (viewModel.user.getCurrentUser() != null) {
            goToHomeFragment()
        }
    }

    fun goToHomeFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()).commit()
        finish()
    }
}