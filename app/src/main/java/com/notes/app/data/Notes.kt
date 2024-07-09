package com.notes.app.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import java.io.File
import java.io.FileOutputStream
import java.lang.System.currentTimeMillis


data class Note(
    val title: String = "",
    val description: String = "",
    val createdAt: Long = currentTimeMillis(),
    val updatedAt: Long = currentTimeMillis(),
) {
    val id get() = createdAt
}