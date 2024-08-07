package com.ashmit.googlecredentialmanager

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ashmit.googlecredentialmanager.ui.theme.GoogleCredentialManagerTheme
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

const val WEB_CLIENT_ID = "486153042366-o41o1mvn80nn01t38piniqscphibdng9.apps.googleusercontent.com"

enum class Screen {
    Login,
    Home
}

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        setContent {
            GoogleCredentialManagerTheme {
                val navController = rememberNavController()
                val context = LocalContext.current
                val scope = rememberCoroutineScope()
                val credentialManager = CredentialManager.create(context)
                val startDestination = if(auth.currentUser == null )Screen.Login.name else Screen.Home.name

                NavHost(navController = navController, startDestination = startDestination) {
                    composable(Screen.Login.name) {
                        LoginScreen(
                            onSignInClick = {
                                // Implement your sign-in logic here
                                val googleIdOption = GetGoogleIdOption.Builder()
                                    /*Yes, if you use setFilterByAuthorizedAccounts(true) with GetGoogleIdOption.Builder, the app will only present accounts that have already been authenticated with your app. This means:

If the User Has Not Logged In Before: The setFilterByAuthorizedAccounts(true) option will not show any accounts because no accounts have been authorized yet. Consequently, the user won't be able to log in since no accounts meet the filter criteria.

If the User Has Logged In Before: The app will show only the accounts that have been previously authenticated. This is useful if you want to limit the sign-in options to accounts that are already authorized.

Possible Solutions
Use Default Option: If you want to allow users to log in even if they haven't logged in before, use the default option or setFilterByAuthorizedAccounts(false). This will show all accounts available on the device, allowing users to choose their Google account for sign-in.

kotlin
Copy code
val googleIdOption = GetGoogleIdOption.Builder()
    .setFilterByAuthorizedAccounts(false)
    .build()
Check User Authentication: Before showing the Google Sign-In options, you can check if the user is already authenticated. If they are not, you can show a different UI or prompt them to log in.

Handle Unauthenticated Users Gracefully: If you still want to use setFilterByAuthorizedAccounts(true), ensure that your app can handle the case where no accounts are available. For instance, you might want to provide a prompt or instruction to guide the user to sign in or authenticate first.*/
//                                    .setFilterByAuthorizedAccounts(true)
                                    .setFilterByAuthorizedAccounts(false)
                                    .setServerClientId(WEB_CLIENT_ID)
                                    .build()

                                val request = GetCredentialRequest.Builder()
                                    .addCredentialOption(googleIdOption)
                                    .build()

                                scope.launch {
                                    try{
                                        val result = credentialManager.getCredential(
                                            context = context,
                                            request = request
                                        )
                                        val credential = result.credential
                                        val googleIdTokenCredential = GoogleIdTokenCredential
                                            .createFrom(credential.data)
                                        val googleIdToken = googleIdTokenCredential.idToken
                                        val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)

                                        auth.signInWithCredential(firebaseCredential)
                                            .addOnCompleteListener{
                                                taks->
                                                if(taks.isSuccessful){
                                                    navController.popBackStack()
                                                    navController.navigate(Screen.Home.name)
                                                }
                                            }
                                    }catch (e:Exception){
                                        Toast.makeText(context, "Error : ${e.message}", Toast.LENGTH_SHORT).show()
                                        e.printStackTrace()
                                    }
                                }

                            }
                        )
                    }
                    composable(Screen.Home.name) {
                        val currentUser = auth.currentUser
                        if (currentUser != null) {
                            HomeScreen(
                                currentUser = auth.currentUser!!,
                                onSignOutClick = {
                                    auth.signOut()
                                    scope.launch {
                                        credentialManager.clearCredentialState(
                                            ClearCredentialStateRequest()
                                        )
                                    }
                                    navController.popBackStack()
                                    navController.navigate(Screen.Login.name)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
