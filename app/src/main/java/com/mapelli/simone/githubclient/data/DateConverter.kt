package com.mapelli.simone.githubclient.data

import android.util.Log

import java.util.Date

import androidx.room.TypeConverter

object DateConverter {
    private val TAG = DateConverter::class.java!!.getSimpleName()

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        Log.d(TAG, "to Date from milllisec : " + timestamp + " is " + Date(timestamp!!))
        return if (timestamp == null) null else Date(timestamp)

    }

    @TypeConverter
    fun fromDate(date: Date): Long? {
        Log.d(TAG, "to millisecfromepoch from date: " + date + " is " + date.time)
        return date?.time

    }
}
