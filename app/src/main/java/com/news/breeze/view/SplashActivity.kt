package com.news.breeze.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.news.breeze.R

class SplashActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        /*--This will help us to hide the status bar and bottom navigation bar of SplashActivity--*/

        WindowInsetsControllerCompat(window, findViewById(R.id.flContainerSplashActivity)).let { controller ->

            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        Handler(Looper.getMainLooper()).postDelayed(
        {
            /*--After 3 Seconds, we will move to HomeActivity & will shut down SplashActivity--*/

            val i = Intent(this, HomeActivity::class.java)
            startActivity(i)
            finish()
        }, 3000)
    }
}