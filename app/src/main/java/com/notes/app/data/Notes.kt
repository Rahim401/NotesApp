package com.notes.app.data

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.lang.System.currentTimeMillis


data class Note(
    val title: String,
    val description: String = "",
    val createdAt: Long = currentTimeMillis(),
    val modifiedAt: Long = currentTimeMillis(),
)

object NotesManager {
    private lateinit var notesDir: File
    fun initialize(ctx: Context) {
        notesDir = File(ctx.filesDir, "Notes")
        if(!notesDir.exists()) notesDir.mkdir()
    }

    fun hasNote(title: String): Boolean {
        return if(!::notesDir.isInitialized) false
        else File(notesDir, "$title.md").exists()
    }

    fun loadNote(title: String): Note? {
        if(!::notesDir.isInitialized) return null
        val noteFl = File(notesDir, "$title.md")

        if(!noteFl.exists()) return null
        else {
            val content = noteFl.readText().split("\n", limit = 4)
            try {
                val nTitle  = content[0]
                val createdAt = content[1].toLong()
                val modifiedAt = content[2].toLong()
                return Note(
                    nTitle, content[3],
                    createdAt, modifiedAt
                )
            }
            catch (e: Exception) { e.printStackTrace(); return null }
        }
    }

    fun dumpNote(note: Note): Boolean {
        if(!::notesDir.isInitialized) return false
        val noteFl = File(notesDir, "${note.title}.md")
        val modifiedAt = currentTimeMillis()

        FileOutputStream(noteFl).writer().use {
            it.write("${note.title}\n")
            it.write("${note.createdAt}\n")
            it.write("$modifiedAt\n")
            it.write(note.description)
        }
        return true
    }
}