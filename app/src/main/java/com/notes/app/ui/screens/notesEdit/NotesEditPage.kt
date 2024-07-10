package com.notes.app.ui.screens.notesEdit

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.notes.app.data.dateFormat
import com.notes.app.ui.screens.NotesEdit
import com.notes.app.ui.screens.NotesEditAct
import com.notes.app.ui.screens.NotesList
import com.notes.app.ui.screens.UiAction
import com.notes.app.ui.screens.notesEdit.components.ContentEditText
import com.notes.app.ui.screens.notesEdit.components.MarkDownText
import com.notes.app.ui.screens.notesEdit.components.getEditTextColor
import com.notes.app.ui.theme.NotesAppTheme


@Composable
fun NotesEditPage(
    modifier: Modifier = Modifier,
    noteEditSt: NotesEdit = NotesEdit(),
    isCreatingNewNote: Boolean = false,
    onAction: (UiAction) -> Unit = {}
) {

    Column(modifier) {
        if(!isCreatingNewNote) Text(
            "Created on ${dateFormat.format(noteEditSt.id)}",
            Modifier
                .padding(top = 10.dp, end = 15.dp)
                .align(Alignment.End),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )
//        HorizontalDivider()
        TextField(
            value = noteEditSt.title,
            onValueChange = { onAction(NotesEditAct.OnTitleChanged(it)) },
            colors = getEditTextColor(),
            textStyle = MaterialTheme.typography.headlineSmall,
            placeholder = {
                Text(
                    text = "Notes Title",
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            modifier = Modifier.fillMaxWidth(),
        )

        HorizontalDivider()

        if(noteEditSt.isInEditMode) ContentEditText(noteEditSt.description, Modifier.fillMaxSize()) {
            onAction(NotesEditAct.OnDescriptionChanged(it))
        }
        else MarkDownText(
            noteEditSt.description,
            Modifier.fillMaxSize().padding(15.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun Preview() {
    NotesAppTheme {
        NotesEditPage()
    }
}