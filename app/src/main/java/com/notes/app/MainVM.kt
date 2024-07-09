package com.notes.app

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.notes.app.data.Note
import com.notes.app.data.NotesManager
import com.notes.app.data.SortNotesBy
import com.notes.app.data.currentTimeDelayed
import com.notes.app.ui.screens.DrawerAct
import com.notes.app.ui.screens.DrawerStates
import com.notes.app.ui.screens.MainAct
import com.notes.app.ui.screens.NotesEditAct
import com.notes.app.ui.screens.NotesList
import com.notes.app.ui.screens.NotesListAct
import com.notes.app.ui.screens.UiAction
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentSet


class MainVM: ViewModel() {
    fun initializeModel(context: Context) {
//        loadNotes()
        notesList.addAll(
            listOf(
                Note("Bila","Gang Gang Gangster"),
                Note("Thaliva","Time to lead", currentTimeDelayed() + 100000000),
                Note("Mangatha","An Venkat Prabu Game"),
                Note("Kalki","Kamal as Villan",currentTimeDelayed() + 50000000,currentTimeDelayed() + 800000000),
                Note("GOAT","Enna elavune thrila"),
                Note("Ommbu","",currentTimeDelayed() + 100000000,currentTimeDelayed() + 600000000),
            )
        )
    }

    private val userName = "H Rahim"
    private val notesList = mutableStateListOf<Note>()
    private val notesSelected = mutableStateListOf<Long>()
    private var sortNotesBy by mutableStateOf(SortNotesBy.ByTitle)
    private val inSelectionMode get() = notesSelected.isNotEmpty()
    private fun loadNotes() {
        notesList.clear()
        notesList.addAll(NotesManager.loadAllNotes())
    }

    fun handelAction(action: UiAction) {
        println("Handling action $action")
        when(action) {
            is MainAct -> {}
            is DrawerAct -> handelDrawerAction(action)
            is NotesListAct -> handelNoteListAction(action)
            is NotesEditAct -> {}
        }
    }

    private var isSortByMenuOpen by mutableStateOf(false)
    private fun handelDrawerAction(action: DrawerAct) {
        when(action) {
            is DrawerAct.DiaryWrittenPrs -> {}
            is DrawerAct.MediaPrs -> {}
            is DrawerAct.ClearNotesPrs -> {
                notesList.clear()
                notesSelected.removeAll(notesSelected)
                NotesManager.removeAllNotes()
            }
            is DrawerAct.SortByPrs -> isSortByMenuOpen = true
            is DrawerAct.SortByDismissed -> isSortByMenuOpen = false
            is DrawerAct.SortByItemPrs -> {
                sortNotesBy = action.by
                isSortByMenuOpen = false
            }
        }
    }

    private fun handelNoteListAction(action: NotesListAct) {
        when(action) {
            is NotesListAct.SelectAllPrs -> notesSelected.addAll(notesList.map { it.id })
            is NotesListAct.UnSelectAllPrs -> notesSelected.clear()
            is NotesListAct.NoteLPrs -> notesSelected.add(action.noteId)
            is NotesListAct.NotePrs -> {
                if(inSelectionMode) {
                    if(notesSelected.contains(action.noteId))
                        notesSelected.removeIf { it == action.noteId }
                    else notesSelected.add(action.noteId)
                }
            }
            is NotesListAct.AddNewNotes -> {}
            is NotesListAct.DeleteNotes -> {
                notesList.removeIf { it.id in notesSelected }
                notesSelected.removeAll(notesSelected)
                notesSelected.forEach { noteId ->
                    NotesManager.removeNote(noteId)
                }
            }
        }
    }


    fun getDrawerStates() = DrawerStates(
        userName, notesList.size, isSortByMenuOpen,
        sortNotesBy
    )
    fun getNotesListStates() = NotesList(
        notesList.toPersistentList(), sortNotesBy,
        notesSelected.toPersistentSet()
    )
}