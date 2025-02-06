package com.example.dishcovery

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.dishcovery.MainActivity
import com.example.dishcovery.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Delay for 3 seconds before moving to MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, TnCActivity::class.java))
            finish() // Finish splash activity so it doesn’t stay in history
        }, 3000)
    }
}