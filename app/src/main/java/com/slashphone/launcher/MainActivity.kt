package com.slashphone.launcher

import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.slashphone.launcher.data.SlashDatabase
import com.slashphone.launcher.ui.home.HomeScreen
import com.slashphone.launcher.ui.inbox.InboxScreen
import com.slashphone.launcher.ui.onboarding.OnboardingScreen
import com.slashphone.launcher.ui.theme.SlashTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var database: SlashDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SlashTheme {
                val navController = rememberNavController()
                var startDestination by remember { mutableStateOf<String?>(null) }

                LaunchedEffect(Unit) {
                    val onboardingDone = database.userPrefsDao()
                        .get("onboarding_complete") == "true"
                    startDestination = if (onboardingDone) "home" else "onboarding"
                }

                startDestination?.let { start ->
                    NavHost(navController = navController, startDestination = start) {
                        composable("onboarding") {
                            OnboardingScreen(
                                onComplete = {
                                    navController.navigate("home") {
                                        popUpTo("onboarding") { inclusive = true }
                                    }
                                },
                            )
                        }
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

        // Prompt for notification listener access if not enabled
        if (!isNotificationListenerEnabled()) {
            val intent = android.content.Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
            startActivity(intent)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        // Don't call super when on home screen — this is a launcher, back should do nothing
        // For other screens, let the navigation handle it
        @Suppress("DEPRECATION")
        super.onBackPressed()
    }

    private fun isNotificationListenerEnabled(): Boolean {
        val listeners = Settings.Secure.getString(
            contentResolver,
            "enabled_notification_listeners",
        )
        return listeners?.contains(packageName) == true
    }
}
