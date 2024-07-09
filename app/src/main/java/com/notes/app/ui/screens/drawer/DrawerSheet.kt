package com.notes.app.ui.screens.drawer

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.notes.app.R
import com.notes.app.ui.screens.DrawerAct
import com.notes.app.ui.screens.DrawerStates
import com.notes.app.ui.screens.UiAction
import com.notes.app.ui.screens.drawer.components.ProfileHeader
import com.notes.app.ui.screens.drawer.components.DrawerItem
import com.notes.app.ui.screens.drawer.components.ItemDivider
import com.notes.app.ui.theme.NotesAppTheme


@Composable
fun DrawerSheet(
    drawerStates: DrawerStates = DrawerStates(),
    onAction: (UiAction) -> Unit = {}
) {
    ModalDrawerSheet(modifier = Modifier.width(300.dp),){
        Column(Modifier.verticalScroll(rememberScrollState())){
            Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
            ProfileHeader(drawerStates.userName) { onAction(DrawerAct.MediaPrs(it)) }

            Box(
                Modifier.fillMaxWidth().background(
                    MaterialTheme.colorScheme.primary.copy(.5f)
                )
            ){
                Divider(
                    Modifier.fillMaxWidth(drawerStates.notesWritten / 10f),
                    5.dp, MaterialTheme.colorScheme.primary
                )
            }

            DrawerItem("Notes Written: ${drawerStates.notesWritten}") {
                onAction(DrawerAct.DiaryWrittenPrs)
            }
            ItemDivider()

            DrawerItem("Sort Tasks By", R.drawable.ic_sort){ onAction(DrawerAct.SortByPrs) }
            DrawerItem("Clear All Tasks", R.drawable.ic_delete_sweep){ onAction(DrawerAct.ClearNotesPrs) }
        }
    }
}




@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DrawerPreview() {
    NotesAppTheme {
        DrawerSheet()
    }
}
@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
fun DrawerPreview2() {
    NotesAppTheme {
        DrawerSheet()
    }
}