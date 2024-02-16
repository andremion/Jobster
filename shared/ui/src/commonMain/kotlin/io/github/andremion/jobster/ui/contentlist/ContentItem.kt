/*
 *    Copyright 2024. André Luiz Oliveira Rêgo
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.github.andremion.jobster.ui.contentlist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.andremion.jobster.domain.entity.Job
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentItem(
    modifier: Modifier,
    content: Job.Content,
    onClick: () -> Unit,
    onSwipeToDelete: () -> Unit
) {
    var isDismissed by remember { mutableStateOf(false) }
    LaunchedEffect(isDismissed) {
        if (isDismissed) {
            delay(AnimationConstants.DefaultDurationMillis.toLong())
            onSwipeToDelete()
        }
    }
    val dismissState = rememberDismissState(
        confirmValueChange = { value ->
            if (value == DismissValue.DismissedToStart) {
                isDismissed = true
                true
            } else {
                false
            }
        }
    )
    AnimatedVisibility(
        visible = !isDismissed,
        exit = shrinkVertically(animationSpec = tween())
            + fadeOut(animationSpec = tween()),
    ) {
        SwipeToDismiss(
            modifier = modifier,
            state = dismissState,
            background = {
                val color = if (dismissState.dismissDirection == DismissDirection.EndToStart) {
                    MaterialTheme.colorScheme.error
                } else {
                    Color.Transparent
                }
                val alpha = if (dismissState.dismissDirection == DismissDirection.EndToStart) {
                    1f
                } else {
                    0f
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = color)
                        .alpha(alpha),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Icon(
                        modifier = Modifier.padding(16.dp),
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.onError
                    )
                }
            },
            dismissContent = {
                val color = if (dismissState.dismissDirection == DismissDirection.EndToStart) {
                    MaterialTheme.colorScheme.background
                } else {
                    Color.Transparent
                }
                Row(
                    modifier = Modifier
                        .background(color = color)
                        .clickable { onClick() }
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = content.title,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        Text(
                            text = content.description,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            },
            directions = setOf(DismissDirection.EndToStart)
        )
    }
}
