package com.cyrax.plugins

import io.ktor.http.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.application.*

fun Application.configureHTTP() {
    install(CORS) {
         allowMethod(HttpMethod.Post)
        allowCredentials = true
        allowNonSimpleContentTypes = true
        allowHost("localhost:3000", schemes = listOf("http", "https"))
    }
}
