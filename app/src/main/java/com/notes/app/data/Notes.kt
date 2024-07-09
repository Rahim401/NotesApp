package com.notes.app.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.SystemClock
import java.io.File
import java.io.FileOutputStream
import java.lang.System.currentTimeMillis
import java.lang.System.nanoTime
import java.time.Clock
import java.time.Instant
import java.util.concurrent.TimeUnit

fun currentTimeDelayed(): Long {
    Thread.sleep(2)
    return currentTimeMillis()
}

data class Note(
    val title: String = "",
    val description: String = "",
    val createdAt: Long = currentTimeDelayed(),
    val updatedAt: Long = currentTimeMillis(),
) {
    val id get() = createdAt
    val createdAtInMilli get() = createdAt
}