package com.grabieckacper.libgame

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.grabieckacper.libgame.view.screens.login.LoginScreen
import com.grabieckacper.libgame.view.theme.LibGameTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent {
            LibGameTheme(darkTheme = false) {
                LoginScreen()
            }
        }
    }
}
