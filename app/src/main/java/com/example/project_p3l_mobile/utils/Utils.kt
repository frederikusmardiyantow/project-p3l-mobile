package com.example.project_p3l_mobile.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.Date
import java.util.Locale

object Utils {
    const val DF_TIMESTAMP = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    const val DF_DATE_READABLE = "d MMMM yyyy"
    const val DF_DATE_SHORT = "d MMM yyyy"
    const val DF_YMD = "yyyy-MM-dd"
    fun formatDate(time: Date, pattern: String = "yyyy-MM-dd"): String {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        return formatter.format(time)
    }
    fun parseDate(dateString: String, pattern: String = DF_TIMESTAMP): Date {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        val date = formatter.parse(dateString)!!
        return date
    }
}
