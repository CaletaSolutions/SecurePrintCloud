@file:OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)

package com.caletasolutions.core.data.printer

import android.content.Context
import com.caletasolutions.core.connectivity.domain.channels.CardTapNotifier
import com.caletasolutions.core.connectivity.printer.tasks.AuthenticationTask
import com.caletasolutions.core.domain.printer.WorkpathService
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

class WorkpathServiceImpl(context: Context) : WorkpathService {

    private var authenticationTask: AuthenticationTask = AuthenticationTask(context)

    override suspend fun initiateAuthentication() {
        authenticationTask.initiateAuthentication()
    }

    override suspend fun getCardData(): String? {
        return try {
            if(CardTapNotifier.channel.isEmpty || CardTapNotifier.channel.isClosedForReceive){
                Timber.i("Channel is empty or closed for receive")
                return null
            }
            var latestValue: String? = null
            for (value in CardTapNotifier.channel) {
                latestValue = value
            }
            latestValue
        } catch (e: Exception) {
            Timber.e(e, "Error getting card data")
            null
        }
    }

}