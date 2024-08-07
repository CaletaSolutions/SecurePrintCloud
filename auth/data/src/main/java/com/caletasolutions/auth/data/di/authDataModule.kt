package com.caletasolutions.auth.data.di

import com.caletasolutions.auth.data.LoginServiceImpl
import com.caletasolutions.auth.domain.LoginService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authDataModule = module {
    singleOf(::LoginServiceImpl).bind<LoginService>()
}