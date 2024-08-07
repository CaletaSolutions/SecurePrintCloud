package com.caletasolutions.core.presentation.designsystem.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random

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
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.roundToPx() }

    val smallDimension = minOf(configuration.screenWidthDp.dp, configuration.screenHeightDp.dp)

    val smallDimensionPx = with(density) { smallDimension.roundToPx() }

    val primaryColor = MaterialTheme.colorScheme.primary


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
                .padding(start = 50.dp, bottom = 50.dp)
                .then(if (hasToolbar) Modifier else Modifier.systemBarsPadding())
        ) {
            content()
        }
    }
}

@Composable
fun DrawRandomCircles() {
    Canvas(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        val numberOfCircles = 18
        val circleRadius = 35f

        for (i in 1..numberOfCircles) {
            val randomColor = colors.random()
            val randomX = Random.nextFloat() * size.width
            val randomY = Random.nextFloat() * size.height
            drawCircle(
                color = randomColor,
                radius = circleRadius,
                center = androidx.compose.ui.geometry.Offset(randomX, randomY)
            )
        }
    }
}


@Preview(showBackground = true, device = Devices.TABLET)
@Composable
fun GradientBackgroundPreview() {
    com.caletasolutions.core.presentation.designsystem.SecurePrintTheme {
        GradientBackground(hasToolbar = true) {
            Column {
            }
        }
    }

}