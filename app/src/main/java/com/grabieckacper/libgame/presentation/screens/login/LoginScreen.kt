package com.grabieckacper.libgame.presentation.screens.login

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.grabieckacper.libgame.R

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToDashboard: () -> Unit
) {
    val context = LocalContext.current

    val signInLauncher = rememberLauncherForActivityResult(
        contract = FirebaseAuthUIActivityResultContract()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            onNavigateToDashboard()
        } else {
            Toast.makeText(
                context,
                "Nie udało się zalogować!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Button(onClick = {
            signInLauncher.launch(viewModel.signInIntent)
        }) {
            Text(text = stringResource(id = R.string.login))
        }
    }
}
