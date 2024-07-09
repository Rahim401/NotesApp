package com.notes.app.ui.screens

import com.notes.app.data.Note
import com.notes.app.data.SortNotesBy
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed class UiAction
sealed class FragmentState

data class MainStates(
    val isDrawerOpen: Boolean = false
)
sealed class MainAct: UiAction() {
    data object MenuButtonPrs: MainAct()
    data object MenuDrawerDismissed: MainAct()
}

data class DrawerStates(
    val userName: String = "H Rahim",
    val notesWritten: Int = 0
)
sealed class DrawerAct: UiAction() {
    data class MediaPrs(val media: String): DrawerAct()
    data object DiaryWrittenPrs: DrawerAct()
    data object SortByPrs: DrawerAct()
    data object ClearNotesPrs: DrawerAct()
}

data class NotesList(
    val notesList: ImmutableList<Note> = persistentListOf(),
    val sortBy: SortNotesBy = SortNotesBy.ByDate,
    val notesSelected: ImmutableList<Long> = persistentListOf()
): FragmentState()
sealed class NotesListAct: UiAction() {
    data class NotePrs(val note: Long): NotesListAct()
    data class NoteLPrs(val note: Long): NotesListAct()
    data object SelectAllPrs: NotesListAct()
    data object UnSelectAllPrs: NotesListAct()
    data object AddNewNotes: NotesListAct()
    data object DeleteNotes: NotesListAct()
}

data class NotesEdit(val initialNote: Note = Note("")): FragmentState()
sealed class NotesEditAct: UiAction() {
    data object OnBackPrs: NotesEditAct()
    data class SaveNotes(val note: Note): NotesEditAct()
}


//sealed class OtherAct: UiAction() {
//    data class GoToLink(val url: String): OtherAct()
//    data class GoToActivity(val cls: Class<*>): OtherAct()
//    data class OpenWebPage(val url: String, val title: String = ""): OtherAct()
//    data class CopyText(val text: String, val label: String = ""): OtherAct()
//    data class MakeToast(val message: String): OtherAct()
//    data object GoBack: OtherAct()
//}