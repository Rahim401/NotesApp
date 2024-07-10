package com.notes.app.ui.screens.notesEdit.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import com.notes.app.ui.screens.NotesEditAct

@Composable
fun getEditTextColor() = TextFieldDefaults.colors(
    focusedContainerColor = MaterialTheme.colorScheme.surface,
    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
    disabledContainerColor = MaterialTheme.colorScheme.surface,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent
)

@Composable
fun ContentEditText(
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
) {
    TextField(
        value = value, onValueChange = onValueChange,
        colors = getEditTextColor(),
        textStyle = MaterialTheme.typography.bodyLarge,
        placeholder = {
            Text(
                text = "Contents of the Note",
                style = MaterialTheme.typography.bodyLarge
            )
        },
        shape = RectangleShape,
        modifier = modifier,
    )
}