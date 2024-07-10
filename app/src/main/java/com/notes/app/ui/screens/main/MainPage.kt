package com.notes.app.ui.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.notes.app.data.Note
import com.notes.app.ui.screens.DrawerStates
import com.notes.app.ui.screens.FragmentState
import com.notes.app.ui.screens.MainStates
import com.notes.app.ui.screens.NotesEdit
import com.notes.app.ui.screens.NotesEditAct
import com.notes.app.ui.screens.NotesList
import com.notes.app.ui.screens.NotesListAct
import com.notes.app.ui.screens.UiAction
import com.notes.app.ui.screens.drawer.DrawerSheet
import com.notes.app.ui.screens.main.components.ActionButton
import com.notes.app.ui.screens.main.components.TopAppBar
import com.notes.app.ui.screens.notesEdit.NotesEditPage
import com.notes.app.ui.screens.notesList.NoteListPage
import com.notes.app.ui.theme.NotesAppTheme
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.coroutines.launch

@Composable
fun MainPage(
    modifier: Modifier = Modifier,
    mainSt: MainStates = MainStates(),
    fragSt: FragmentState = NotesList(),
    drawerSt: DrawerStates = DrawerStates(),
    onAction: (UiAction) -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val isOnTop by remember { derivedStateOf { scrollState.firstVisibleItemIndex == 0 } }

    val onListFrag = fragSt is NotesList
//    val inSelectionMode = remember((fragSt as? NotesList)?.notesList) {
////        derivedStateOf {
//            fragSt is NotesList && fragSt.notesSelected.isNotEmpty()
////        }
//    }
    val inSelectionMode = fragSt is NotesList && fragSt.notesSelected.isNotEmpty()
    val isCreatingNewNote = fragSt is NotesEdit && fragSt.title.isBlank()


    ModalNavigationDrawer(
        drawerContent = { DrawerSheet(drawerSt, onAction) },
        drawerState = drawerState,
        gesturesEnabled = onListFrag && !inSelectionMode
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    onListFrag, inSelectionMode,
                    isCreatingNewNote,
                    onLeftBtnPrs = {
                        when {
                            onListFrag && !inSelectionMode -> {
                                coroutineScope.launch {
                                    drawerState.open()
                                }
                            }
                            onListFrag && inSelectionMode -> onAction(NotesListAct.UnSelectAllPrs)
                            else -> onAction(NotesEditAct.OnBackPrs)
                        }
                    },
                    onRightBtnPrs = {
                        if(onListFrag && inSelectionMode)
                            onAction(NotesListAct.SelectAllPrs)
                    }
                )
            },
            floatingActionButton = {
                ActionButton(
                    onListFrag, inSelectionMode, isOnTop,
                    onClick = {
                        when {
                            onListFrag && !inSelectionMode -> onAction(NotesListAct.AddNewNotes)
                            onListFrag && inSelectionMode -> onAction(NotesListAct.DeleteNotes)
                            else -> onAction(NotesEditAct.SaveNotes(Note()))
                        }
                    }
                )
            }
        ) { pd ->
            if(fragSt is NotesList) NoteListPage(
                Modifier
                    .fillMaxSize()
                    .padding(pd),
                fragSt, scrollState, onAction
            )
            else if(fragSt is NotesEdit) NotesEditPage(
                Modifier
                    .fillMaxSize()
                    .padding(pd), fragSt,
                remember { isCreatingNewNote },
                onAction
            )
        }
    }
}

@Preview
@Composable
fun Preview() {
    val x = Note("Hello", "May name is billa")
    NotesAppTheme {
        MainPage(
            fragSt = NotesList(
                notesList = persistentListOf(
                    x
                ),
                notesSelected = persistentSetOf(x.id)
            )
        )
    }
}