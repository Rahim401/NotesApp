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
import com.notes.app.ui.screens.MainAct
import com.notes.app.ui.screens.MainStates
import com.notes.app.ui.screens.NotesEdit
import com.notes.app.ui.screens.NotesEditAct
import com.notes.app.ui.screens.NotesList
import com.notes.app.ui.screens.NotesListAct
import com.notes.app.ui.screens.UiAction
import com.notes.app.ui.screens.drawer.DrawerSheet
import com.notes.app.ui.screens.main.components.ActionButton
import com.notes.app.ui.screens.main.components.TopAppBar
import com.notes.app.ui.screens.notesList.NoteListPage
import com.notes.app.ui.theme.NotesAppTheme
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
    val inSelectionMode by remember { derivedStateOf { fragSt is NotesList && fragSt.notesSelected.isNotEmpty()  } }
    val isCreatingNewNote = remember { fragSt is NotesEdit && fragSt.initialNote.title.isBlank() }
//    LaunchedEffect(key1 = mainSt.isDrawerOpen) {
//        if(mainSt.isDrawerOpen) drawerState.open()
//        else drawerState.close()
//    }

    ModalNavigationDrawer(
        drawerContent = { DrawerSheet(drawerSt) },
        drawerState = drawerState,
        gesturesEnabled = onListFrag && !inSelectionMode
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    onListFrag, inSelectionMode, isCreatingNewNote,
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

        }
    }
}

@Preview
@Composable
fun Preview() {
    NotesAppTheme {
        MainPage()
    }
}