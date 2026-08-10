package com.cranoxz.streamroom.core.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cranoxz.streamroom.lobby.ui.LobbyContent

@Composable
fun AppNavigations() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "lobby") {
        composable(route = "lobby") {
            LobbyContent()
        }
    }
}