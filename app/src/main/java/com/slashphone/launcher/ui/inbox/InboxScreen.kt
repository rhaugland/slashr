package com.slashphone.launcher.ui.inbox

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.slashphone.launcher.data.entity.CaughtNotification
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun InboxScreen(
    onBack: () -> Unit,
    viewModel: InboxViewModel = hiltViewModel(),
) {
    val notifications by viewModel.notifications.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 24.dp, vertical = 48.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "/inbox",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.clickable { onBack() },
            )
            Spacer(modifier = Modifier.weight(1f))
            if (notifications.isNotEmpty()) {
                TextButton(onClick = { viewModel.dismissAll() }) {
                    Text(
                        text = "clear all",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (notifications.isEmpty()) {
            Text(
                text = "Nothing here. Silence is golden.",
                style = MaterialTheme.typography.bodyMedium,
            )
        } else {
            LazyColumn {
                items(notifications, key = { it.id }) { notification ->
                    NotificationRow(
                        notification = notification,
                        onDismiss = { viewModel.dismiss(notification.id) },
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
private fun NotificationRow(
    notification: CaughtNotification,
    onDismiss: () -> Unit,
) {
    val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
    val time = timeFormat.format(Date(notification.timestamp))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onDismiss() }
            .padding(vertical = 8.dp),
    ) {
        Row {
            Text(
                text = notification.appName,
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = time,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.White.copy(alpha = 0.4f),
                ),
            )
        }
        if (notification.title.isNotBlank()) {
            Text(
                text = notification.title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 2.dp),
            )
        }
        if (notification.content.isNotBlank()) {
            Text(
                text = notification.content,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.White.copy(alpha = 0.7f),
                ),
                modifier = Modifier.padding(top = 2.dp),
                maxLines = 2,
            )
        }
    }
}
