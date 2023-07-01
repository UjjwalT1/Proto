package com.cyrax

import io.ktor.server.application.*
import com.cyrax.plugins.*
import com.cyrax.plugins.model.DBAccess

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    val db = DBAccess
    configureHTTP()
    configureMonitoring()
    configureSerialization(db)
    configureRouting()
}
