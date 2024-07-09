package com.notes.app.ui.screens.notesList.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.notes.app.data.Note
import com.notes.app.ui.theme.NotesAppTheme

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun MessageItem(
    note: Note, modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {}
){
    val megItemVPadding = 10.dp
    Row(
        modifier = modifier
            .clip(ShapeDefaults.Small)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .combinedClickable(onClick = onClick, onLongClick = onLongClick)
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val isDescAvailable = note.description.isNotBlank()
        AnimatedVisibility(isSelected) {
            Checkbox(
                true, null,
                Modifier.padding(horizontal = 10.dp)
            )
        }
        AnimatedVisibility(!isSelected) { Spacer(modifier = Modifier.width(5.dp)) }
        Column(Modifier.padding(start = 5.dp, end = 15.dp), verticalArrangement = Arrangement.Bottom){
            Text(
                note.title, style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                maxLines = 1, overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(end = 20.dp, top = megItemVPadding,
                    bottom = if(isDescAvailable) 0.dp else megItemVPadding
                )
            )
            if(isDescAvailable) Text(
                note.description, style = MaterialTheme.typography.bodySmall.copy(lineHeight = 12.5.sp),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                maxLines = 2, overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = megItemVPadding)
            )
        }
    }
}

@Preview
@Composable
fun Preview() {
    NotesAppTheme {
        MessageItem(
            Note(
                "Rahime",
                "Vanakam da mapulla bangalore la irundu"
            ),
            isSelected = false
        )
    }
}