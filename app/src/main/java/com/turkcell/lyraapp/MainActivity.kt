package com.turkcell.lyraapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.turkcell.lyraapp.ui.login.LoginRoute
import com.turkcell.lyraapp.ui.theme.LyraAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LyraAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "login",
                ) {
                    composable("login") {
                        LoginRoute()
                    }
                }
            }
        }
    }
}
