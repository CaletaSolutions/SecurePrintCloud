package com.caletasolutions.core.presentation.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource

val EyeClosedIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.eye_closed)

val EyeOpenedIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.eye_opened)

val LockIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.lock)
val EmailIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.email)
val CheckIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.check)
val CardTapIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.access_card)

val LogoImage: Painter
    @Composable
    get() = painterResource(id = R.drawable.launcher_logo)

