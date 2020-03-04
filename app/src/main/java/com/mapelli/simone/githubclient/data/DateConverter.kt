package com.mapelli.simone.githubclient.data

import android.util.Log

import androidx.room.TypeConverter
import java.util.*

object DateConverter {
    private val TAG = DateConverter::class.java.getSimpleName()

    @TypeConverter
    fun longToCalendar(timestamp: Long): Calendar? {
        Log.d(TAG, "to Date from milllisec : " + timestamp + " is " + Date(timestamp!!))
        // return if (timestamp == null) null else Date(timestamp)
        return Calendar.getInstance().apply { timeInMillis = timestamp }


    }

    @TypeConverter
    fun calendarToLong(calendar: Calendar): Long? {
        Log.d(TAG, "to millisecfromepoch from date: " + calendar + " is "
                + calendar?.timeInMillis)
        // return date?.time
        return calendar?.timeInMillis
    }
}
