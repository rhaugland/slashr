package com.slashphone.launcher.ui.results

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.slashphone.launcher.command.CommandResult

@Composable
fun CommandResultSheet(result: CommandResult) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(horizontal = 32.dp, vertical = 16.dp),
    ) {
        when (result) {
            is CommandResult.Text -> {
                Text(
                    text = result.content,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            is CommandResult.Error -> {
                Text(
                    text = result.message,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color(0xFFFF6B6B),
                    ),
                )
            }
            is CommandResult.NotFound -> {
                Text(
                    text = "Unknown command: /${result.input}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.White.copy(alpha = 0.5f),
                    ),
                )
                if (result.suggestions.isNotEmpty()) {
                    Text(
                        text = "Try: ${result.suggestions.joinToString(", ") { "/$it" }}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp),
                    )
                }
            }
            is CommandResult.Launched,
            is CommandResult.Navigate -> {
                // Nothing to render
            }
        }
    }
}
