package com.note.postapi

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.note.postapi.Activity.HomePage
import com.note.postapi.Activity.SignIn

class SplashScreen : AppCompatActivity() {

    companion object {

        lateinit var sp: SharedPreferences
        lateinit var edit: SharedPreferences.Editor
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        sp = getSharedPreferences("share", MODE_PRIVATE)
        edit = sp.edit()

        var i = sp.getBoolean("user", false)

        Handler(Looper.getMainLooper()).postDelayed({

            if (i) {
                startActivity(Intent(this, HomePage::class.java))
                finish()
            } else {
                startActivity(Intent(this, SignIn::class.java))
                finish()

            }

        }, 2000)
    }
}