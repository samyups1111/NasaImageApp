package com.example.nasaimageapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nasaimageapp.view.NasaImageGridScreen

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "image_grid_screen") {

        composable(route = "image_grid_screen") {
            NasaImageGridScreen()
        }
    }
}