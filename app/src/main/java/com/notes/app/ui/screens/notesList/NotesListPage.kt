package com.notes.app.ui.screens.notesList

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.notes.app.data.SortNotesBy
import com.notes.app.data.sortToDoListBy
import com.notes.app.ui.screens.NotesList
import com.notes.app.ui.screens.NotesListAct
import com.notes.app.ui.screens.UiAction
import com.notes.app.ui.screens.notesList.components.MessageItem
import com.notes.app.ui.screens.notesList.components.NoteListHeader


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteListPage(
    modifier: Modifier = Modifier,
    noteListSt: NotesList = NotesList(),
    lazyListState: LazyListState = rememberLazyListState(),
    onAction: (UiAction) -> Unit = {}
) {
    val (sortedList, headerMap) = remember(noteListSt.notesList, noteListSt.sortNotesBy) {
        sortToDoListBy(noteListSt.notesList, noteListSt.sortNotesBy)
    }
    if (sortedList.isEmpty()) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            Text("No Notes available!", style = MaterialTheme.typography.titleLarge)
        }
    }
    else LazyColumn(modifier.padding(10.dp), lazyListState) {
        itemsIndexed(sortedList, { _, item -> item.id }) { idx, note ->
//            println("${note.title} $idx ${headerMap[idx]}")
            headerMap[idx]?.let { header ->
                NoteListHeader(
                    header,
                    Modifier.padding(start= 5.dp)
                )
            }

            MessageItem(
                note = note,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
//                    .animateItemPlacement(tween(300)),
                isSelected = noteListSt.notesSelected.contains(note.id),
                onClick = { onAction(NotesListAct.NotePrs(note.id)) },
                onLongClick = { onAction(NotesListAct.NoteLPrs(note.id)) },
            )
        }
    }
}