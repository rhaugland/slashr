package com.slashphone.launcher.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun OnboardingScreen(
    onComplete: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center,
    ) {
        when (state.step) {
            OnboardingStep.WELCOME -> WelcomeStep(
                onNext = { viewModel.nextStep() },
            )
            OnboardingStep.WHITELIST_CONTACTS -> WhitelistContactsStep(
                selected = state.selectedContacts,
                onNext = { viewModel.nextStep() },
            )
            OnboardingStep.WHITELIST_APPS -> WhitelistAppsStep(
                selected = state.selectedApps,
                onNext = { viewModel.nextStep() },
            )
            OnboardingStep.READY -> ReadyStep(
                onComplete = { viewModel.completeOnboarding(onComplete) },
            )
        }
    }
}

@Composable
private fun WelcomeStep(onNext: () -> Unit) {
    Column(
        modifier = Modifier.padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "/",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Your phone should work for you.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Slash replaces your home screen with a single command bar. No icons. No notifications. Just you, deciding what you need.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(48.dp))
        TextButton(onClick = onNext) {
            Text(
                text = "let's go /",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
private fun WhitelistContactsStep(
    selected: List<com.slashphone.launcher.data.entity.WhitelistEntry>,
    onNext: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Who can always reach you?",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "These people will always get through. Everyone else goes to /inbox.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(24.dp))

        if (selected.isNotEmpty()) {
            selected.forEach { contact ->
                Text(
                    text = contact.displayName,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 4.dp),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        Text(
            text = "Contact picker coming in next build.\nFor now, all notifications go to /inbox.",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.White.copy(alpha = 0.4f),
            ),
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(48.dp))
        TextButton(onClick = onNext) {
            Text(
                text = if (selected.isEmpty()) "skip for now /" else "next /",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
private fun WhitelistAppsStep(
    selected: List<com.slashphone.launcher.data.entity.WhitelistEntry>,
    onNext: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Which apps can interrupt you?",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Security systems, baby monitors, anything critical. Everything else waits in /inbox.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "App picker coming in next build.\nFor now, only system alerts get through.",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.White.copy(alpha = 0.4f),
            ),
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(48.dp))
        TextButton(onClick = onNext) {
            Text(
                text = if (selected.isEmpty()) "skip for now /" else "next /",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
private fun ReadyStep(onComplete: () -> Unit) {
    Column(
        modifier = Modifier.padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "/",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "You're ready.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Type anything. Start with /today or /weather.\nOr just type what you're looking for.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(48.dp))
        TextButton(onClick = onComplete) {
            Text(
                text = "start /",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}
