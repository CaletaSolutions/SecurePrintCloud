package com.caletasolutions.secureprintcloud

import android.app.Application
import com.caletasolutions.auth.data.di.authDataModule
import com.caletasolutions.auth.presentation.di.loginViewModelModule
import com.caletasolutions.core.connectivity.printer.tasks.WorkpathInitializationTask
import com.caletasolutions.core.data.di.coreDataModule
import com.caletasolutions.core.data.logger.CaletaDebugTree
import com.caletasolutions.core.event.SDKEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber


class CaletaSecurePrintCloud : Application() {

    private lateinit var sdkInitTask: WorkpathInitializationTask
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    override fun onCreate() {
        super.onCreate()
        setupLogging()
        setupDependencyInjection()
        initializeSdk()
    }

    private fun setupLogging() {
//                if (BuildConfig.DEBUG) {
//                    Timber.plant(CaletaDebugTree())
//                }
        Timber.plant(CaletaDebugTree())
    }

    private fun setupDependencyInjection() {
        startKoin {
            androidContext(this@CaletaSecurePrintCloud)
            modules(loginViewModelModule,authDataModule,coreDataModule)
        }
    }

    private fun initializeSdk() {
        sdkInitTask = WorkpathInitializationTask(applicationContext)

        applicationScope.launch {
            try {
                withContext(Dispatchers.Default) {
                    sdkInitTask.initialize()
                }
                observeSdkInitialization()
            } catch (e: Exception) {
                Timber.e("SDK Initialization Failed: ${e.localizedMessage}")
            }
        }
    }

    private suspend fun observeSdkInitialization() {
        sdkInitTask.sdkInitializationUpdate.collect { event ->
            when (event) {
                is SDKEvent.SDKInitializationError -> {
                    Timber.e("SDK Initialization Error: ${event.tr.localizedMessage}")
                }

                is SDKEvent.SDKInitializationSuccess -> {
                    Timber.i("SDK Initialization Success")
                }
            }
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        applicationScope.cancel()
    }
}


