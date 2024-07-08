package com.notes.app

import android.app.Application
import com.notes.app.data.NotesManager


class MainApplication: Application() {
    override fun onCreate() {
        NotesManager.initialize(applicationContext)
        super.onCreate()
    }
}