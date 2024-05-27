package com.example.countdownwidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import java.util.concurrent.TimeUnit
import android.widget.RemoteViews

class CountdownWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val thisAppWidgetComponentName = ComponentName(context.packageName, javaClass.name)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidgetComponentName)
            onUpdate(context, appWidgetManager, appWidgetIds)
        }
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val sharedPreferences = context.getSharedPreferences("CountdownWidgetPreferences", Context.MODE_PRIVATE)
        val targetDate = sharedPreferences.getLong("targetDate", -1)

        val currentDate = Calendar.getInstance()
        val daysLeft = if (targetDate != -1L) {
            val targetCalendar = Calendar.getInstance().apply { timeInMillis = targetDate }
            TimeUnit.MILLISECONDS.toDays(targetCalendar.timeInMillis - currentDate.timeInMillis).toInt()
        } else {
            0
        }

        val views = RemoteViews(context.packageName, R.layout.widget_countdown)
        views.setTextViewText(R.id.tv_weeks_left, "${daysLeft / 7}w")
        views.setTextViewText(R.id.tv_days_left, "${daysLeft % 7}d")
        views.setTextViewText(R.id.tv_until, "until")
        views.setTextViewText(R.id.tv_date, getDateString(targetDate))

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    private fun getDateString(targetDate: Long): String {
        if (targetDate == -1L) return "N/A"
        val calendar = Calendar.getInstance().apply { timeInMillis = targetDate }
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1 // Months are 0-based in Calendar
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return "$month/$day/${year.toString().substring(2)}"
    }
}