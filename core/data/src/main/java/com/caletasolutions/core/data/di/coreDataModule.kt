package com.caletasolutions.core.data.di

import com.caletasolutions.core.data.printer.WorkpathServiceImpl
import com.caletasolutions.core.domain.printer.WorkpathService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    singleOf(::WorkpathServiceImpl).bind<WorkpathService>()
}