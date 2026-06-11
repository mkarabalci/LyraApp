package com.turkcell.lyraapp.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeRoute(viewModel: HomeViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeContract.Effect.ShowError -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            HomeBottomBar(
                selectedTab = state.selectedTab,
                onTabSelected = { viewModel.onIntent(HomeContract.Intent.TabSelected(it)) },
            )
        },
    ) { paddingValues ->
        HomeScreen(
            state = state,
            onIntent = viewModel::onIntent,
            modifier = Modifier.padding(paddingValues),
        )
    }
}

@Composable
private fun HomeBottomBar(
    selectedTab: HomeContract.HomeTab,
    onTabSelected: (HomeContract.HomeTab) -> Unit,
) {
    NavigationBar {
        HomeContract.HomeTab.entries.forEach { tab ->
            NavigationBarItem(
                selected = selectedTab == tab,
                onClick = { onTabSelected(tab) },
                icon = { Icon(imageVector = tab.navIcon, contentDescription = tab.navLabel) },
                label = { Text(tab.navLabel) },
            )
        }
    }
}

private val HomeContract.HomeTab.navLabel: String
    get() = when (this) {
        HomeContract.HomeTab.AnaSayfa  -> "Ana sayfa"
        HomeContract.HomeTab.Ara       -> "Ara"
        HomeContract.HomeTab.Kutuphane -> "Kütüphane"
        HomeContract.HomeTab.Favoriler -> "Favoriler"
        HomeContract.HomeTab.Profil    -> "Profil"
    }

private val HomeContract.HomeTab.navIcon: ImageVector
    get() = when (this) {
        HomeContract.HomeTab.AnaSayfa  -> Icons.Default.Home
        HomeContract.HomeTab.Ara       -> Icons.Default.Search
        HomeContract.HomeTab.Kutuphane -> Icons.Default.LibraryMusic
        HomeContract.HomeTab.Favoriler -> Icons.Default.FavoriteBorder
        HomeContract.HomeTab.Profil    -> Icons.Default.Person
    }
