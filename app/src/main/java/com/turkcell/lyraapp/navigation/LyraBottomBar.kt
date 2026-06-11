package com.turkcell.lyraapp.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun LyraBottomBar(
    currentRoute: String?,
    onDestinationSelected: (LyraDestination) -> Unit,
) {
    NavigationBar {
        LyraDestination.tabDestinations.forEach { destination ->
            NavigationBarItem(
                selected = currentRoute == destination.route,
                onClick = { onDestinationSelected(destination) },
                icon = {
                    Icon(
                        imageVector = destination.navIcon,
                        contentDescription = destination.navLabel,
                    )
                },
                label = { Text(destination.navLabel) },
            )
        }
    }
}

private val LyraDestination.navLabel: String
    get() = when (this) {
        LyraDestination.Home      -> "Ana sayfa"
        LyraDestination.Search    -> "Ara"
        LyraDestination.Library   -> "Kütüphane"
        LyraDestination.Favorites -> "Favoriler"
        LyraDestination.Profile   -> "Profil"
        else                      -> ""
    }

private val LyraDestination.navIcon: ImageVector
    get() = when (this) {
        LyraDestination.Home      -> Icons.Default.Home
        LyraDestination.Search    -> Icons.Default.Search
        LyraDestination.Library   -> Icons.Default.LibraryMusic
        LyraDestination.Favorites -> Icons.Default.FavoriteBorder
        LyraDestination.Profile   -> Icons.Default.Person
        else                      -> Icons.Default.Home
    }
