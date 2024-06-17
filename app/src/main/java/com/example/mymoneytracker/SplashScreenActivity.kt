package com.example.mymoneytracker

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.mymoneytracker.databinding.ActivitySplashScreenBinding
import com.example.mymoneytracker.model.User


class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_splash_screen)

        val mDelay: Long = 1000

        Handler(Looper.getMainLooper()).postDelayed({
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent) }, mDelay)
    }
}