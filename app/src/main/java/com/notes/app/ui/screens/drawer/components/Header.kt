package com.notes.app.ui.screens.drawer.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.notes.app.R
import com.notes.app.ui.theme.NotesAppTheme

@Composable
fun ProfileHeader(userName:String, onClick:(String)->Unit){
    Column(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(top = 40.dp, bottom = 20.dp, start = 15.dp, end = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            Modifier
                .fillMaxWidth(0.5f)
                .aspectRatio(1f)
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = .75f))
        ){
            Icon(
                Icons.TwoTone.Person,null,
                Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                tint = MaterialTheme.colorScheme.primaryContainer
            )
        }
        Text(
            "Logged in As User",
            modifier = Modifier.padding(top=30.dp),
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = .7f),
            style = MaterialTheme.typography.labelLarge,
        )
        Text(
            userName, modifier = Modifier.padding(top = 2.5.dp),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.headlineSmall,
        )

        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            MediaButton(R.drawable.ic_github){ onClick("Github") }
            MediaButton(R.drawable.ic_linkedin){ onClick("LinkedIn") }
            MediaButton(R.drawable.ic_twitter){ onClick("Twitter") }
        }
    }
}

@Preview
@Composable
fun Preview() {
    NotesAppTheme {
        ProfileHeader(userName = "Rahim H") {
            
        }
    }
}