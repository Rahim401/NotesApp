package com.notes.app.ui.screens.drawer.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.notes.app.R
import com.notes.app.data.SortNotesBy
import com.notes.app.ui.dRes
import com.notes.app.ui.theme.NotesAppTheme

@Composable
fun SortByDropDownMenu(
    isExpanded: Boolean = false,
    currentSortBy: SortNotesBy = SortNotesBy.ByTitle,
    onSelect: (SortNotesBy) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    DropdownMenu(expanded = isExpanded, onDismissRequest = onDismiss) {
        if(currentSortBy != SortNotesBy.ByTitle) DropdownMenuItem(
            text = { Text("Alphabetically") },
            onClick = { onSelect(SortNotesBy.ByTitle) },
            leadingIcon = { Icon(R.drawable.ic_sort_alphabetical_ascending.dRes, contentDescription = null) }
        )
        if(currentSortBy != SortNotesBy.ByTitleDescending) DropdownMenuItem(
            text = { Text("Alphabetically Descending") },
            onClick = { onSelect(SortNotesBy.ByTitleDescending) },
            leadingIcon = { Icon(R.drawable.ic_sort_alphabetical_descending.dRes, contentDescription = null) }
        )
        if(currentSortBy != SortNotesBy.ByCreateTimeDes) DropdownMenuItem(
            text = { Text("Recently Created first") },
            onClick = { onSelect(SortNotesBy.ByCreateTimeDes) },
            leadingIcon = { Icon(R.drawable.ic_sort_clock_descending.dRes, contentDescription = null) }
        )
//        if(currentSortBy != SortNotesBy.ByCreateTime) DropdownMenuItem(
//            text = { Text("Recently Created last") },
//            onClick = { onSelect(SortNotesBy.ByCreateTime) },
//            leadingIcon = { Icon(R.drawable.ic_sort_clock_descending.dRes, contentDescription = null) }
//        )
        if(currentSortBy != SortNotesBy.ByUpdateTimeDes) DropdownMenuItem(
            text = { Text("Recently Updated first") },
            onClick = { onSelect(SortNotesBy.ByUpdateTimeDes) },
            leadingIcon = { Icon(R.drawable.ic_sort_clock_descending.dRes, contentDescription = null) }
        )
//        if(currentSortBy != SortNotesBy.ByUpdateTime) DropdownMenuItem(
//            text = { Text("Recently Updated last") },
//            onClick = { onSelect(SortNotesBy.ByUpdateTime) },
//            leadingIcon = { Icon(R.drawable.ic_sort_clock_descending.dRes, contentDescription = null) }
//        )
    }
}

@Preview
@Composable
fun Preview2() {
    NotesAppTheme {
        SortByDropDownMenu()
    }
}