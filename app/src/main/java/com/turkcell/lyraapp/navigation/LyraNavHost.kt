package com.turkcell.lyraapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.turkcell.lyraapp.ui.login.LoginRoute
import com.turkcell.lyraapp.ui.register.RegisterRoute

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = LyraDestination.Login.route,
        modifier = modifier,
    ) {
        composable(LyraDestination.Login.route) {
            LoginRoute(
                onNavigateToRegister = {
                    navController.navigate(LyraDestination.Register.route) {
                        launchSingleTop = true
                    }
                },
            )
        }
        composable(LyraDestination.Register.route) {
            RegisterRoute(
                onNavigateToLogin = {
                    navController.navigate(LyraDestination.Login.route) {
                        popUpTo(LyraDestination.Login.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onNavigateToHome = {
                    // TODO: Ev ekrani hazir oldigunda buraya navigasyon eklenecek
                },
            )
        }
    }
}
