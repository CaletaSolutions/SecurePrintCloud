package com.caletasolutions.core.connectivity.domain.channels

import kotlinx.coroutines.channels.Channel

object CardTapNotifier {
    val channel = Channel<String>()
}