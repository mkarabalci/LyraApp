package com.turkcell.lyraapp.navigation

enum class LyraDestination(val route: String) {
    Login("login"),
    Register("register"),
    Home("home"),
    Search("search"),
    Library("library"),
    Favorites("favorites"),
    Profile("profile");

    companion object {
        val tabDestinations = listOf(Home, Search, Library, Favorites, Profile)
    }
}
