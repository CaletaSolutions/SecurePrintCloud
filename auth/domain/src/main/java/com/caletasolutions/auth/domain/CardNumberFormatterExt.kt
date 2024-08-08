package com.caletasolutions.auth.domain


fun String.formatCardNumber(): String {
    return this.drop(2).trim()
}