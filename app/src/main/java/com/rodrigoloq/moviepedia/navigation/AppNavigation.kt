package com.rodrigoloq.moviepedia.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rodrigoloq.moviepedia.account.EditInformationView
import com.rodrigoloq.moviepedia.login.LoginView
import com.rodrigoloq.moviepedia.login.viewmodel.LoginViewModel
import com.rodrigoloq.moviepedia.moviedetail.MovieDetailView
import com.rodrigoloq.moviepedia.navigation.viewmodel.AppNavigationViewModel
import com.rodrigoloq.moviepedia.register.RegisterView
import com.rodrigoloq.moviepedia.retrofit.entities.MovieDetailResponse
import com.rodrigoloq.moviepedia.reviews.ReviewsView
import com.rodrigoloq.moviepedia.reviews.UserReviewsView
import com.rodrigoloq.moviepedia.room.MoviepediaApp
import com.rodrigoloq.moviepedia.room.entities.UserAuth
import com.rodrigoloq.moviepedia.session.SessionManager

@Composable
fun AppNavigation(viewModel: AppNavigationViewModel = viewModel()){

    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    val userId by viewModel.userId.collectAsState()

    val navController = rememberNavController()

    MoviepediaApp.auth = UserAuth(userId ?: 0,"usuario")

    val startDestination = if(isLoggedIn) "main" else "auth"

    NavHost(navController = navController,
        startDestination = startDestination){
        navigation(startDestination = "login", route = "auth"){
            composable("login") {
                LoginView(navController)
            }
            composable("register") {
                RegisterView(navController)
            }
        }
        navigation(startDestination = "home", route = "main"){
            composable("home"){
                NavigationBarView(navController)
            }
        }
        navigation(startDestination = "edit_info", route = "user"){
            composable("edit_info"){
                EditInformationView(navController)
            }
            composable("user_reviews"){
                UserReviewsView(navController)
            }
        }

        navigation(startDestination = "detail", route = "movie"){
            composable("detail" + "/{mid}", arguments = listOf(navArgument(name = "mid"){
                type = NavType.IntType
            })) {
                MovieDetailView(navController = navController, movieId = it.arguments?.getInt("mid"))
            }
            composable("review" + "/{mid}", arguments = listOf(navArgument(name = "mid"){
                type = NavType.IntType
            })) {
                ReviewsView(navController = navController, movieId = it.arguments?.getInt("mid"))
            }
        }
    }
}