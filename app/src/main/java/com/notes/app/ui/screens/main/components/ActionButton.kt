package com.notes.app.ui.screens.main.components

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.notes.app.ui.screens.FragmentState
import com.notes.app.ui.screens.NotesEdit
import com.notes.app.ui.screens.NotesEditAct
import com.notes.app.ui.screens.NotesList
import com.notes.app.ui.screens.NotesListAct
import com.notes.app.ui.screens.UiAction
import com.notes.app.ui.theme.NotesAppTheme

@Composable
fun ActionButtonOld(isExpanded:Boolean = false, onClick: () -> Unit){
    ExtendedFloatingActionButton(
        text = { Text("Add Notes") },
        icon = { Icon(Icons.Filled.Add,null) }, onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        expanded = isExpanded
    )
}

@Composable
fun ActionButton2(
    fragSt: FragmentState = NotesList(),
    isExpanded: Boolean,
    onAction: (UiAction) -> Unit = {}
) {
    val inSelectMode by remember {
        derivedStateOf {
            fragSt is NotesList && fragSt.notesSelected.isNotEmpty()
        }
    }
    ExtendedFloatingActionButton(
        text = {
            AnimatedVisibility(fragSt is NotesList) { Text("Add Notes") }
            AnimatedVisibility(fragSt is NotesList && inSelectMode) {
                Text("Delete Notes")
            }
            AnimatedVisibility(fragSt is NotesEdit) { Text("Sava Notes") }
        },
        icon = {
            AnimatedVisibility(fragSt is NotesList) { Icon(Icons.Filled.Add,null) }
            AnimatedVisibility(fragSt is NotesList && inSelectMode) { Icon(Icons.Filled.Remove,null) }
            AnimatedVisibility(fragSt is NotesEdit) { Icon(Icons.Filled.Save,null) }
        },
        onClick = {
            when(fragSt) {
                is NotesList -> if(inSelectMode)
                    onAction(NotesListAct.DeleteNotes)
                else onAction(NotesListAct.AddNewNotes)
                is NotesEdit -> {}//onAction(NotesEditAct.OnSavePrs)
            }
        },
        containerColor = MaterialTheme.colorScheme.primary,
        expanded = isExpanded
    )
}

@Composable
fun ActionButton(
    onListFrag: Boolean = true,
    isInSelectionMode: Boolean = false,
    isExpanded: Boolean = true,
    onClick: () -> Unit = {},
) {
    ExtendedFloatingActionButton(
        text = {
            AnimatedVisibility(onListFrag && !isInSelectionMode) { Text("Add Notes") }
            AnimatedVisibility(onListFrag && isInSelectionMode) { Text("Delete Notes") }
            AnimatedVisibility(!onListFrag) { Text("Save Notes") }
        },
        icon = {
            AnimatedVisibility(onListFrag && !isInSelectionMode) { Icon(Icons.Filled.Add,null) }
            AnimatedVisibility(onListFrag && isInSelectionMode) { Icon(Icons.Filled.Remove,null) }
            AnimatedVisibility(!onListFrag) { Icon(Icons.Filled.Save,null) }
        },
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        expanded = isExpanded
    )
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
fun ActionButtonPreview() {
    NotesAppTheme {
        ActionButton(true) {  }
    }
}