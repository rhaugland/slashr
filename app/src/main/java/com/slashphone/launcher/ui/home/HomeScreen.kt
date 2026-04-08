package com.slashphone.launcher.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
        ) {
            // Context line
            Text(
                text = state.contextLine,
                style = MaterialTheme.typography.bodyMedium,
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Command bar
            BasicTextField(
                value = state.commandInput,
                onValueChange = viewModel::onCommandInputChanged,
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge,
                cursorBrush = SolidColor(Color.White),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                keyboardActions = KeyboardActions(
                    onGo = { viewModel.onCommandSubmitted() },
                ),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color.White.copy(alpha = 0.08f),
                                shape = MaterialTheme.shapes.small,
                            )
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                    ) {
                        if (state.commandInput.isEmpty()) {
                            Text(
                                text = "/",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = Color.White.copy(alpha = 0.3f),
                                ),
                            )
                        }
                        innerTextField()
                    }
                },
            )
        }
    }
}
