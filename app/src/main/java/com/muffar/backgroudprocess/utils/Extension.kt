package com.muffar.backgroudprocess.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object Helper {
    fun getCurrentTime(): String {
        return SimpleDateFormat("hh:mm", Locale.getDefault()).format(Date())
    }

    fun addTime(currentTime: String, minute: Int): String {
        val calendar = Calendar.getInstance()
        calendar.time = SimpleDateFormat("hh:mm", Locale.getDefault()).parse(currentTime)!!

        calendar.add(Calendar.MINUTE, minute)

        return SimpleDateFormat("hh:mm", Locale.getDefault()).format(calendar.time)
    }
}