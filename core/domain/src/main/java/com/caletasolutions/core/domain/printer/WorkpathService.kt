package com.caletasolutions.core.domain.printer

interface WorkpathService {
    suspend fun initiateAuthentication()
    suspend fun getCardData() : String?
}