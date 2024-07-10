package com.notes.app.ui.screens.main.components

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Deselect
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material.icons.filled.SelectAll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.notes.app.data.Note
import com.notes.app.ui.screens.FragmentState
import com.notes.app.ui.screens.MainAct
import com.notes.app.ui.screens.NotesEdit
import com.notes.app.ui.screens.NotesEditAct
import com.notes.app.ui.screens.NotesList
import com.notes.app.ui.screens.NotesListAct
import com.notes.app.ui.screens.UiAction
import com.notes.app.ui.theme.NotesAppTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
private fun AppBarButton(icon: ImageVector, onClick:()->Unit){
    IconButton(onClick, modifier = Modifier.size(50.dp)){
        Icon(
            icon, null,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarOld(
    inSelectMode: Boolean = false,
    onMenuPressed: () -> Unit = {},
    onSelectPressed: () -> Unit = {},
    onUnSelectPressed: () -> Unit = {},
) = CenterAlignedTopAppBar(
    title = { Text("Notes App") },
    navigationIcon = {
        AnimatedVisibility(inSelectMode) {
            AppBarButton(Icons.Filled.SelectAll, onSelectPressed)
        }
        AnimatedVisibility(!inSelectMode) {
            AppBarButton(Icons.Filled.Menu, onMenuPressed)
        }
    },
    actions = {
        AnimatedVisibility(inSelectMode) {
            AppBarButton(Icons.Filled.Deselect, onUnSelectPressed)
        }
    },
    colors = TopAppBarDefaults.mediumTopAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        titleContentColor = MaterialTheme.colorScheme.onPrimary,
        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
    )
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar2(
    fragSt: FragmentState = NotesList(),
    onAction: (UiAction) -> Unit = {}
) {
    val isCreating by remember {
        derivedStateOf {
            fragSt is NotesEdit && fragSt.title.isBlank()
        }
    }
    val inSelectMode by remember {
        derivedStateOf {
            fragSt is NotesList && fragSt.notesSelected.isNotEmpty()
        }
    }
    CenterAlignedTopAppBar(
        title = {
            AnimatedVisibility(fragSt is NotesList) { Text("Notes App") }
            AnimatedVisibility(fragSt is NotesEdit) {
                Text(
                    if(isCreating)
                        "Creating Notes"
                    else "Editing Notes"
                )
            }
        },
        navigationIcon = {
            AnimatedVisibility(fragSt is NotesList && !inSelectMode) {
                AppBarButton(Icons.Filled.Menu) { onAction(MainAct.MenuButtonPrs) }
            }
            AnimatedVisibility(fragSt is NotesList && inSelectMode) {
                AppBarButton(Icons.Filled.Deselect) { onAction(NotesListAct.UnSelectAllPrs) }
            }
            AnimatedVisibility(fragSt is NotesEdit) {
                AppBarButton(Icons.AutoMirrored.Filled.ArrowBack) { onAction(NotesEditAct.OnBackPrs) }
            }
        },
        actions = {
            AnimatedVisibility(fragSt is NotesList && inSelectMode) {
                AppBarButton(Icons.Filled.SelectAll) { onAction(NotesListAct.SelectAllPrs) }
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    onListFrag: Boolean = true,
    isInSelectionMode: Boolean = false,
    isCreatingNewNote: Boolean = true,
    isInEditMode: Boolean = true,
    onLeftBtnPrs: () -> Unit = {},
    onRightBtnPrs: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = {
            Crossfade(onListFrag, label = "") { onFrag ->
                Text(
                    remember {
                        if(onFrag) "Notes App"
                        else if(isCreatingNewNote) "Creating Notes"
                        else "Editing Notes"
                    },
                    textAlign = TextAlign.Center
                )
            }

//            AnimatedVisibility(onListFrag) { Text("Notes App") }
//            AnimatedVisibility(!onListFrag) {
//                Text(
//                    if(isCreatingNewNote) "Creating Notes"
//                    else "Editing Notes"
//                )
//            }
        },
        navigationIcon = {
            AnimatedVisibility(onListFrag && !isInSelectionMode) {
                AppBarButton(Icons.Filled.Menu, onLeftBtnPrs)
            }
            AnimatedVisibility(isInSelectionMode) {
                AppBarButton(Icons.Filled.Deselect, onLeftBtnPrs)
            }
            AnimatedVisibility(!onListFrag) {
                AppBarButton(Icons.AutoMirrored.Filled.ArrowBack, onLeftBtnPrs)
            }
        },
        actions = {
            AnimatedVisibility(onListFrag && isInSelectionMode) {
                AppBarButton(Icons.Filled.SelectAll, onRightBtnPrs)
            }
            AnimatedVisibility(!onListFrag && isInEditMode) {
                AppBarButton(Icons.Filled.Preview, onRightBtnPrs)
            }
            AnimatedVisibility(!onListFrag && !isInEditMode) {
                AppBarButton(Icons.Filled.Edit, onRightBtnPrs)
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}


@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
fun AppBarPreview() {
    NotesAppTheme {
//        TopAppBar2 {  }
        TopAppBar(
            onListFrag = false,
            isInEditMode = false
//            NotesList(notesSelected = persistentListOf())
//            NotesEdit(Note(""))
        )
    }
}