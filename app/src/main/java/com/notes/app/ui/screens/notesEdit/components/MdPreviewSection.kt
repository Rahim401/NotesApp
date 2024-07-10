package com.notes.app.ui.screens.notesEdit.components

import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.MarkwonConfiguration
import io.noties.markwon.image.ImagesPlugin
//import io.noties.markwon.image.ImageProps

@Composable
fun MarkDownText(mdString: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val markwon = Markwon.builder(context)
        .build()

    AndroidView(
        factory = {
            val textView = TextView(it)
            markwon.setMarkdown(textView, mdString)
            return@AndroidView textView
        },
        modifier = modifier
    )
}