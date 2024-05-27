package com.example.countdownwidget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.countdownwidget.databinding.ActivitySetTargetDateBinding

class SetTargetDateActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySetTargetDateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_target_date)

        val datePicker = findViewById<DatePicker>(R.id.datePicker)
        val btnSetDate = findViewById<Button>(R.id.btnSetDate)

        btnSetDate.setOnClickListener {
            val year = datePicker.year
            val month = datePicker.month
            val day = datePicker.dayOfMonth

            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)

            val targetDate = calendar.timeInMillis
            saveTargetDate(targetDate)

            // Update the widget
            val intent = Intent(this, CountdownWidgetProvider::class.java)
            intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            sendBroadcast(intent)

            finish()
        }
    }

    private fun saveTargetDate(targetDate: Long) {
        val sharedPreferences = getSharedPreferences("CountdownWidgetPreferences", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putLong("targetDate", targetDate)
            apply()
        }
    }
}