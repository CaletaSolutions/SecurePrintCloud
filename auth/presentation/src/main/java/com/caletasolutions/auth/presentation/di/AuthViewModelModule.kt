package com.caletasolutions.auth.presentation.di


import com.caletasolutions.auth.presentation.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val loginViewModelModule = module {
    viewModelOf(::LoginViewModel)
}