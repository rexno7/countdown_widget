package com.example.countdownwidget

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun openDateSetter(view: View) {
        val intent = Intent(this, SetTargetDateActivity::class.java)
        startActivity(intent)
    }
}

