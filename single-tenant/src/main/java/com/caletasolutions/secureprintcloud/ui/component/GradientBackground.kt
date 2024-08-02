package com.caletasolutions.secureprintcloud.ui.component

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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.caletasolutions.secureprintcloud.ui.theme.SecurePrintTheme
import com.caletasolutions.secureprintcloud.ui.theme.test
import com.caletasolutions.secureprintcloud.ui.theme.test2
import com.caletasolutions.secureprintcloud.ui.theme.test3
import com.caletasolutions.secureprintcloud.ui.theme.test4
import com.caletasolutions.secureprintcloud.ui.theme.test5
import com.caletasolutions.secureprintcloud.ui.theme.test6

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
                            test,
                            test2,
                            test3,
                            test4,
                            test5,
                            test6,
                            MaterialTheme.colorScheme.background
                        ),
                        //center = Offset(x = screenWidthPx.toFloat() + 30, y = screenHeightPx/2f),
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

@Preview(showBackground = true, device = Devices.TABLET)
@Composable
fun GradientBackgroundPreview() {
    SecurePrintTheme {
        GradientBackground(hasToolbar = true) {
            Column {
            }
        }
    }

}