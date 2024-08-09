@file:OptIn(ExperimentalFoundationApi::class)

package com.caletasolutions.core.presentation.designsystem.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.TextFieldLineLimits
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.caletasolutions.core.presentation.designsystem.Poppins


@Composable
fun SecurePrintLabel(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    startIcon: ImageVector?,
    endIcon: ImageVector?,
    title: String?,
    error: String? = null,
    additionalInfo: String? = null,
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (title != null) {
                Text(text = title, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            if (error != null) {
                Text(text = error, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
            } else if (additionalInfo != null) {
                Text(
                    text = additionalInfo,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))

        BasicTextField2(
            state = state,
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 18.sp,
                fontFamily = Poppins,
                textAlign = TextAlign.Start
            ),
            lineLimits = TextFieldLineLimits.SingleLine,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
            enabled = false,
            readOnly = true,
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(
                    MaterialTheme.colorScheme.surface
                )
                .border(
                    width = 1.dp, color = Color.Transparent, shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp),
            decorator = { innerBox ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (startIcon != null) {
                        Icon(
                            imageVector = startIcon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            innerBox()
                        }
                    }
                    if (endIcon != null) {
                        Spacer(modifier = Modifier.width(16.dp))
                        Icon(
                            imageVector = endIcon,
                            contentDescription = null,
                            tint = Color(0xFF00F15E),
                            modifier = Modifier
                                .padding(end = 8.dp)
                        )
                    }
                }
            }
        )
    }
}