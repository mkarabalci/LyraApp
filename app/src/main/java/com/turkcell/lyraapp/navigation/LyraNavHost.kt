package com.turkcell.lyraapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.turkcell.lyraapp.ui.favorites.FavoritesScreen
import com.turkcell.lyraapp.ui.home.HomeRoute
import com.turkcell.lyraapp.ui.library.LibraryScreen
import com.turkcell.lyraapp.ui.login.LoginRoute
import com.turkcell.lyraapp.ui.profile.ProfileScreen
import com.turkcell.lyraapp.ui.register.RegisterRoute
import com.turkcell.lyraapp.ui.search.SearchRoute

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route
    val showBottomBar = LyraDestination.tabDestinations.any { it.route == currentRoute }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (showBottomBar) {
                LyraBottomBar(
                    currentRoute = currentRoute,
                    onDestinationSelected = { destination ->
                        navController.navigate(destination.route) {
                            popUpTo(LyraDestination.Home.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            }
        },
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = LyraDestination.Login.route,
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
        ) {
            composable(LyraDestination.Login.route) {
                LoginRoute(
                    onNavigateToRegister = {
                        navController.navigate(LyraDestination.Register.route) {
                            launchSingleTop = true
                        }
                    },
                    onNavigateToHome = {
                        navController.navigate(LyraDestination.Home.route) {
                            popUpTo(LyraDestination.Login.route) { inclusive = true }
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
                        navController.navigate(LyraDestination.Home.route) {
                            popUpTo(LyraDestination.Login.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                )
            }
            composable(LyraDestination.Home.route) { HomeRoute() }
            composable(LyraDestination.Search.route) { SearchRoute() }
            composable(LyraDestination.Library.route) { LibraryScreen() }
            composable(LyraDestination.Favorites.route) { FavoritesScreen() }
            composable(LyraDestination.Profile.route) { ProfileScreen() }
        }
    }
}
