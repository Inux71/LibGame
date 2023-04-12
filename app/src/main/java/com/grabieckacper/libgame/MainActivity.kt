package com.grabieckacper.libgame

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.grabieckacper.libgame.presentation.screens.add_game.AddGameScreen
import com.grabieckacper.libgame.presentation.screens.dashboard.DashboardScreen
import com.grabieckacper.libgame.presentation.screens.login.LoginScreen
import com.grabieckacper.libgame.presentation.theme.LibGameTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent {
            LibGameTheme(darkTheme = false) {
                val navHostController = rememberNavController()

                NavHost(
                    navController = navHostController,
                    startDestination = "login"
                ) {
                    composable(route = "login") {
                        LoginScreen(onNavigateToDashboard = {
                            navHostController.navigate("dashboard")
                        })
                    }

                    composable(route = "dashboard") {
                        DashboardScreen(
                            onNavigateToLogin = {
                                navHostController.popBackStack()
                            },
                            onNavigateToAddGame = {
                                navHostController.navigate("add")
                            }
                        )
                    }

                    composable(route = "add") {
                        AddGameScreen(onNavigateToDashboard = {
                            navHostController.popBackStack()
                        })
                    }
                }
            }
        }
    }
}
