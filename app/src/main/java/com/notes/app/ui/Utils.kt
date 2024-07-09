package com.notes.app.ui

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.core.content.res.ResourcesCompat

@Composable
fun Int.sRes(vararg formatArgs: Any) =
    stringResource(this, formatArgs = formatArgs)
@Stable
inline val Int.sRes: String @Composable get() = sRes()
fun Int.sRes(context: Context, vararg formatArgs: Any) =
    context.resources.getString(this, *formatArgs)

@Stable
inline val Int.dRes: Painter @Composable get() = painterResource(this)
fun Int.dRes(context: Context) = ResourcesCompat.getDrawable(
    context.resources, this, null
)

//@Stable
//inline val Int.adRes: Painter
//    @Composable get() = rememberDrawablePainter(
//    ResourcesCompat.getDrawable(
//        LocalContext.current.resources,
//        this, null
//    )
//)
fun Int.adRes(context: Context) = ResourcesCompat.getDrawable(
    context.resources, this, null
) as AnimationDrawable
