package com.ashmit.googlecredentialmanager

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.firebase.auth.FirebaseUser

@Composable
fun HomeScreen(
    currentUser: FirebaseUser,
    onSignOutClick: () -> Unit // Fixed the typo in the parameter name
) {
    val textStyle = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    )
    Surface(modifier = Modifier.fillMaxSize()) { // Fixed typo in modifier usage
        Column(
            modifier = Modifier.fillMaxSize(), // Fixed typo in modifier usage
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            currentUser.let { user -> // Removed unnecessary null check
                user.photoUrl?.let { photoUrl ->
                    AsyncImage(
                        modifier = Modifier
                            .size(140.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(photoUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                }
                user.displayName?.let { name ->
                    Text(
                        text = name,
                        style = textStyle,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                }
                user.email?.let { mailId ->
                    Text(
                        text = "Mail Id: $mailId",
                        style = textStyle,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                }
                Text(
                    text = "UID: ${user.uid}",
                    style = textStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Button(onClick = onSignOutClick,
                Modifier.fillMaxWidth()) {
                Text(text = "SignOut", style = textStyle)
                Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription ="SignOut")
            }
        }
    }
}
