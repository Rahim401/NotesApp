package com.notes.app.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import java.io.File
import java.io.FileOutputStream
import java.lang.System.currentTimeMillis

object NotesManager {
    private const val RegistryName = "NotesRegistry"

    private lateinit var notesDir: File
    private lateinit var notesRegistry: SharedPreferences

    fun initialize(ctx: Context) {
        notesDir = File(ctx.filesDir, "Notes")
        notesRegistry = ctx.getSharedPreferences(RegistryName, MODE_PRIVATE)
        if(!notesDir.exists()) notesDir.mkdir()
    }

    fun hasNote(noteId: Long): Boolean {
        return if(!::notesDir.isInitialized) false
        else File(notesDir, "$noteId.md").exists()
    }

    fun loadNote(noteId: Long): Note? = loadNote("$noteId.md")
    fun loadNote(noteName: String): Note? = loadNote(File(notesDir, noteName))
    fun loadNote(noteFl: File): Note? {
        if(!::notesDir.isInitialized) return null
        if(!noteFl.exists()) return null
        else {
            val content = noteFl.readText().split("\n", limit = 4)
            try {
                val nTitle  = content[0]
                val createdAt = content[1].toLong()
                val updatedAt = content[2].toLong()
                return Note(
                    nTitle, content[3],
                    createdAt, updatedAt
                )
            }
            catch (e: Exception) { e.printStackTrace(); return null }
        }
    }
    fun loadAllNotes() = notesDir.list()?.mapNotNull { loadNote(it) } ?: listOf()

    fun dumpNote(note: Note): Boolean {
        if(!::notesDir.isInitialized) return false
        val noteFl = File(notesDir, "${note.id}.md")
        val modifiedAt = currentTimeMillis()

        FileOutputStream(noteFl).writer().use {
            it.write("${note.title}\n")
            it.write("${note.createdAt}\n")
            it.write("$modifiedAt\n")
            it.write(note.description)
        }
        return true
    }

    fun removeNote(noteId: Long) = File(
        notesDir, "$noteId.md"
    ).delete()
    fun removeAllNotes() = loadAllNotes().forEach {
        removeNote(it.id)
    }
}