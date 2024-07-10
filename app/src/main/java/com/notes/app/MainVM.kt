package com.notes.app

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
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
import com.notes.app.ui.screens.FragmentState
import com.notes.app.ui.screens.MainAct
import com.notes.app.ui.screens.NotesEdit
import com.notes.app.ui.screens.NotesEditAct
import com.notes.app.ui.screens.NotesList
import com.notes.app.ui.screens.NotesListAct
import com.notes.app.ui.screens.UiAction
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentSet
import java.util.Date

const val AcharyaLinkedInLink = "https://www.linkedin.com/school/acharya-institutes"
const val AcharyaTwitterInLink = "https://x.com/acharya_ac_in"
class MainVM: ViewModel() {
    fun initializeModel(context: Context) {
        loadNotes()
    }

    private val notesList = mutableStateListOf<Note>()
    private fun loadNotes() {
        notesList.clear()
        notesList.addAll(NotesManager.loadAllNotes())
    }

    private var onListFrag by mutableStateOf(true)
    fun handelAction(action: UiAction, context: Context? = null) {
        println("Handling action $action")
        when(action) {
            is MainAct -> {}
            is DrawerAct -> handelDrawerAction(action, context)
            is NotesListAct -> handelNoteListAction(action)
            is NotesEditAct -> handelNoteEditAction(action)
        }
    }

    private val userName = "6D CSE, AIT"
    private var isSortByMenuOpen by mutableStateOf(false)
    private fun handelDrawerAction(action: DrawerAct, context: Context? = null) {
        when(action) {
            is DrawerAct.DiaryWrittenPrs -> notesList.addAll(
                listOf(
                    Note("Rahim","Ya, that's my name", currentTimeDelayed() - 2*86400000, currentTimeDelayed() - 2*8640000),
                    Note("Bila","Gang Gang Gangster"),
                    Note("Thaliva","Time to lead", currentTimeDelayed()  - 86400000, currentTimeDelayed()  - 86400000),
                    Note("Mangatha","An Venkat Prabu Game"),
                    Note("Kalki","Kamal as Villan", currentTimeDelayed() - 4*86400000, currentTimeDelayed() - 4*86400000),
                    Note("GOAT","Enna elavune thrila"),
//                    Note("Ommbu","",currentTimeDelayed() + 100000000,currentTimeDelayed() + 600000000),
                )
            )
            is DrawerAct.MediaPrs -> {
                when(action.media) {
                    "LinkedIn" -> context?.goToLink(AcharyaLinkedInLink)
                    "Twitter" -> context?.goToLink(AcharyaTwitterInLink)
                    else -> {}
                }
            }
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

    private var sortNotesBy by mutableStateOf(SortNotesBy.ByTitle)
    private val notesSelected = mutableStateListOf<Long>()
    private val inSelectionMode get() = notesSelected.isNotEmpty()
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
                else notesList.find { it.id == action.noteId }?.let {
                    initEditFields(it)
                    onListFrag = false
                }
            }
            is NotesListAct.AddNewNotes -> {
                initEditFields()
                onListFrag = false
            }
            is NotesListAct.DeleteNotes -> {
                notesList.removeIf { it.id in notesSelected }
                notesSelected.removeAll(notesSelected)
                notesSelected.forEach { noteId ->
                    NotesManager.removeNote(noteId)
                }
            }
        }
    }

    private var editNoteTitle by mutableStateOf("")
    private var editNoteDescription by mutableStateOf("")
    private var editingNoteOfId by mutableLongStateOf(0L)
    private var isInEditMode by mutableStateOf(true)
    private fun initEditFields(
        withNote: Note = Note(
            createdAt = currentTimeDelayed()
        )
    ) {
        editingNoteOfId = withNote.id
        editNoteTitle = withNote.title
        editNoteDescription = withNote.description
    }
    private fun handelNoteEditAction(action: NotesEditAct) {
        when(action) {
            is NotesEditAct.OnBackPrs -> onListFrag = true
            is NotesEditAct.OnTitleChanged -> editNoteTitle = action.title
            is NotesEditAct.OnDescriptionChanged -> editNoteDescription = action.desc
            is NotesEditAct.SaveNotes -> {
                if(editNoteTitle.isNotEmpty()) {
                    val noteEdited = Note(
                        editNoteTitle, editNoteDescription,
                        editingNoteOfId
                    )
                    notesList.removeIf { it.id == editingNoteOfId }
                    notesList.add(noteEdited)
                    NotesManager.dumpNote(noteEdited)
                    onListFrag = true
                }
            }
            is NotesEditAct.OnModeChangePrs -> isInEditMode = !isInEditMode
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
    fun getNotesEditStates() = NotesEdit(
        editingNoteOfId, editNoteTitle,
        editNoteDescription, isInEditMode
    )

    fun getFragmentStates(): FragmentState {
        return if(onListFrag) getNotesListStates()
        else getNotesEditStates()
    }
}