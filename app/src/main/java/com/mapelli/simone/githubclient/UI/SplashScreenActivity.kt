package com.mapelli.simone.githubclient.UI

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.os.Handler

import com.mapelli.simone.githubclient.R


/**
 * -------------------------------------------------------------------------------------------------
 * Minimal splash screen
 */
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        startAppWithDelay()
        loadingHandler()

    }

    private fun startAppDirect() {
        val intent = Intent(applicationContext, UsersSearchActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startAppWithDelay() {
        Handler().postDelayed({
            val intent = Intent(applicationContext, UsersSearchActivity::class.java)
            startActivity(intent)
            finish()
        }, 1500)
    }


    // Loading indicator
    private fun loadingHandler() {
        val loadingTxt = findViewById<TextView>(R.id.loading_splash_txt)
        val handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                if (loadingTxt.text.length > 10) {
                    loadingTxt.text = "Loading "
                } else {
                    loadingTxt.text = loadingTxt.text.toString() + "."
                }
                handler.postDelayed(this, 500)
            }
        }
        handler.postDelayed(runnable, 500)
    }
}
