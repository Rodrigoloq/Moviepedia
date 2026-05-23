package com.rodrigoloq.moviepedia.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

enum class Destination(val route: String,
                       val label: String,
                       val icon: ImageVector,
                       val contentDescription: String) {
    HOME("nav_home","Inicio", Icons.Default.Home,"Main view"),
    SEARCH("nav_search","Buscar", Icons.Default.Search,"Search view"),
    FAVORITES("nav_favorites","Favoritos", Icons.Default.Favorite,"Account view"),
    ACCOUNT("nav_account","Cuenta", Icons.Default.AccountCircle,"Explore view"),
}