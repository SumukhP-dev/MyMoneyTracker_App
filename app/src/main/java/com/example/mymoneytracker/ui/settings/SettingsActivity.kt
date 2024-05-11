package com.example.mymoneytracker.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.example.mymoneytracker.R
import com.example.mymoneytracker.databinding.ActivityLoginBinding
import com.example.mymoneytracker.databinding.ActivitySettingsBinding
import com.example.mymoneytracker.ui.home.HomeFragment

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.constraintLayout5, HomeFragment()).commit()
            finish()
        }
    }
}