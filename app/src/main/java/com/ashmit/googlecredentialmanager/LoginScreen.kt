package com.ashmit.googlecredentialmanager

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
//import androidx.compose.material.icons.rounded.Login
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onSignInClick: () -> Unit // Corrected typo in the function parameter name
) {
    Surface(
        modifier = modifier.fillMaxSize() // Use the passed-in modifier here
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(80.dp),
                imageVector = Icons.Rounded.AccountCircle, // Use a valid icon from Icons
                contentDescription = null
            )
            Spacer(modifier = Modifier.size(35.dp))
            Button(onClick = { onSignInClick() }) {
                Text(
                    text = "Continue With Google",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 20.sp
                )
            }
        }
    }
}
