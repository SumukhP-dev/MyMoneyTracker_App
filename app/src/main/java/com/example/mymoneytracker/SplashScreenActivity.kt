package com.example.mymoneytracker

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mymoneytracker.databinding.ActivitySplashScreenBinding
import com.example.mymoneytracker.ui.home.HomeFragment
import com.example.mymoneytracker.ui.login.LoginActivity


class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var app = applicationContext as MMTApplication

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_splash_screen)

        val mDelay: Long = 5000

        Handler(Looper.getMainLooper()).postDelayed({
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent) }, mDelay)
    }
}