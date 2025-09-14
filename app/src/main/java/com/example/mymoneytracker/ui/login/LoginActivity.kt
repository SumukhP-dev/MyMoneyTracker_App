package com.example.mymoneytracker.ui.login

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mymoneytracker.R
import com.example.mymoneytracker.databinding.ActivityLoginBinding
import com.example.mymoneytracker.ui.home.HomeFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


open class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]


        binding.createAccountButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                val success = viewModel.createAccount(binding.email.text.toString()
                    .substring(0, binding.email.text?.length?.minus(1) ?: 0),
                    binding.password.text.toString())
                if (success) {
                    Log.d("create", "Custom backend login success")
                    goToHomeFragment()
                } else {
                    Toast.makeText(
                        baseContext,
                        "Account creation failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        }

        binding.signInButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                val success = viewModel.loginAccount(binding.email.text.toString(),
                    binding.password.text.toString())
                if (success) {
                    Log.d("login", "Custom backend login success")
                    goToHomeFragment()
                } else {
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

        val checkIfSignedOut = intent.getBooleanExtra("checkIfSignedOut", false)

        if (checkIfSignedOut) {
            viewModel.user.setCurrentUserLoggedIn(false)
        }

        // Check if user is signed in and update UI accordingly.
        if (viewModel.user.getCurrentUserLoggedIn()) {
            goToHomeFragment()
        }
    }

    private fun goToHomeFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()).commit()
        finish()
    }
}