package com.caletasolutions.core.presentation.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.caletasolutions.core.presentation.designsystem.LogoImage
import com.caletasolutions.core.presentation.designsystem.Poppins
import com.caletasolutions.core.presentation.designsystem.R
import com.caletasolutions.core.presentation.designsystem.SecurePrintTheme

val colors = listOf(
    Color(0xFF67D2F5),
    Color(0xFF5BCBF8),
    Color(0xFF4EB1DA),
    Color(0xFF2F8B9F),
    Color(0xFF407596),
    Color(0xFF2C607F)
)

@Composable
fun GradientBackground(
    modifier: Modifier = Modifier,
    hasToolbar: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.roundToPx() }
    val smallDimension = minOf(configuration.screenWidthDp.dp, configuration.screenHeightDp.dp)
    val smallDimensionPx = with(density) { smallDimension.roundToPx() }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .then(
                    Modifier.blur(smallDimension / 100f)
                )
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            com.caletasolutions.core.presentation.designsystem.test,
                            com.caletasolutions.core.presentation.designsystem.test2,
                            com.caletasolutions.core.presentation.designsystem.test3,
                            com.caletasolutions.core.presentation.designsystem.test4,
                            com.caletasolutions.core.presentation.designsystem.test5,
                            com.caletasolutions.core.presentation.designsystem.test6,
                            MaterialTheme.colorScheme.background
                        ),
                        center = Offset(x = screenWidthPx.toFloat(), y = -100f),
                        radius = smallDimensionPx / 1.1f
                    )
                )
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .then(if (hasToolbar) Modifier else Modifier.systemBarsPadding())
        ) {
            content()
        }
    }
}

@Composable
fun LoginFormHeader(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .requiredWidth(500.dp)
            .fillMaxHeight()
            .padding(start = 50.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Image(
                painter = LogoImage,
                contentDescription = null,
                modifier = Modifier
                    .requiredSize(125.dp)
                    .clip(CircleShape)
            )
        }
        content()
    }
}


@Preview(showBackground = true, device = Devices.TABLET)
@Composable
fun GradientBackgroundPreview() {
    SecurePrintTheme {
        GradientBackground(hasToolbar = true) {
            LoginFormHeader {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}