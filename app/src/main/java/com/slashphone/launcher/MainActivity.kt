package com.slashphone.launcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.slashphone.launcher.ui.home.HomeScreen
import com.slashphone.launcher.ui.inbox.InboxScreen
import com.slashphone.launcher.ui.theme.SlashTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SlashTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(
                            onNavigate = { route -> navController.navigate(route) },
                        )
                    }
                    composable("inbox") {
                        InboxScreen(onBack = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}
