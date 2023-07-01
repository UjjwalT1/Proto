package com.cyrax.plugins

import com.cyrax.plugins.model.DBAccess
import com.cyrax.plugins.model.Messages
import com.cyrax.plugins.model.Projects
import com.cyrax.plugins.model.logger
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

fun Application.configureSerialization(db:DBAccess) {
    install(ContentNegotiation) {
        json()
    }
    routing {

        get("/json/kotlinx-serialization") {
                call.respond(mapOf("hello" to "world"))
        }
        get("/allProjects"){
            logger.info("\n\n\n DBHashCode: {}",db)
            val scope = CoroutineScope(Dispatchers.IO)
            val tempList = mutableListOf<Projects>()
            scope.async {

                db.getProjects().collect{
                    tempList.add(it)
                    logger.info("{}",it)
                }

            }.await()
            call.respond(tempList)

        }

        post("/sendMsg"){
            logger.info("\n\n\n DBHashCode: {}",db)
            db.addMsg(call.receive<Messages>())
            call.respond("Sent")
        }
    }
}
