package com.notes.app.ui.screens.drawer.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun DrawerItem(name:String, @DrawableRes iconId:Int=-1, onClick: () -> Unit){
    val isJustText = iconId == -1
    Row(
        modifier = Modifier.fillMaxWidth().height(50.dp)
            .clickable(onClick=onClick).padding(horizontal=25.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        if(!isJustText) Icon(
            painter = painterResource(id = iconId),
            modifier = Modifier.padding(end=25.dp),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = null
        )
        Text(
            name,
            style = if(isJustText) MaterialTheme.typography.labelLarge
            else MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}