package com.caletasolutions.secureprintcloud.di

import com.caletasolutions.secureprintcloud.ui.screen.auth.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val singleTenantModule = module {
    viewModelOf(::AuthViewModel)
}