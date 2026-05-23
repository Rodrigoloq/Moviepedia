package com.rodrigoloq.moviepedia.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.rodrigoloq.moviepedia.account.AccountView
import com.rodrigoloq.moviepedia.favorites.FavoritesView
import com.rodrigoloq.moviepedia.home.HomeView
import com.rodrigoloq.moviepedia.search.SearchView

@Composable
fun AppNavHost(innerNavController: NavHostController,
               rootNavController: NavController,
               startDestination: Destination,
               modifier: Modifier = Modifier){
    NavHost(navController = innerNavController,
        startDestination = startDestination.route){
        Destination.entries.forEach { destination ->
            composable(destination.route){
                when(destination){
                    Destination.HOME -> HomeView(rootNavController,modifier)
                    Destination.SEARCH-> SearchView(rootNavController,modifier)
                    Destination.FAVORITES -> FavoritesView(rootNavController,modifier)
                    Destination.ACCOUNT -> AccountView(rootNavController, modifier)
                }
            }
        }
    }
}