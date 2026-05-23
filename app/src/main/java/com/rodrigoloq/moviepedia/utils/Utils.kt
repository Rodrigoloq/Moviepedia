package com.rodrigoloq.moviepedia.utils

import android.text.format.DateFormat
import java.util.Calendar
import java.util.Locale

class Utils {
    fun dateFormater(time: Long, withHour: Boolean): String{
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = time
        var format = ""

        if(withHour) format = "dd/MM/yyyy HH:ss" else format = "dd/MM/yyyy"

        return DateFormat.format(format, calendar).toString()
    }
}