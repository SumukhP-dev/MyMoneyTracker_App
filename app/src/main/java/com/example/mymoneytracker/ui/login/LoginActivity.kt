package com.example.mymoneytracker.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.mymoneytracker.R
import com.example.mymoneytracker.databinding.ActivityLoginBinding
import com.example.mymoneytracker.model.User
import com.example.mymoneytracker.ui.home.HomeFragment
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
            viewModel.loginAccount(binding.email.text.toString(),
                binding.password.text.toString(), auth, this)
        }

        binding.createAccountButton.setOnClickListener {
            viewModel.createAccount(binding.email.text.toString(),
                binding.password.text.toString(), auth, this)
        }

        if (viewModel.transitionToMainActivity.value == true) {
            goToHomeFragment()
        } else {
            Toast.makeText(
                baseContext,
                "Authentication failed.",
                Toast.LENGTH_SHORT,
            ).show()
        }
    }

    public override fun onStart() {
        super.onStart()

        // Check if user is signed in (non-null) and update UI accordingly.
        User.getInstance().setCurrentUser(auth.currentUser)

        var checkIfSignedOut = intent.getBooleanExtra("checkIfSignedOut", false)

        if (checkIfSignedOut) {
            FirebaseAuth.getInstance().signOut()
            User.getInstance().setCurrentUser(null)
        }

        if (User.getInstance().getCurrentUser() != null) {
            goToHomeFragment()
        }
    }

    fun goToHomeFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()).commit()
        finish()
    }
}