package com.cyrax.plugins.model

import com.mongodb.kotlin.client.coroutine.FindFlow
import com.mongodb.kotlin.client.coroutine.MongoClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.slf4j.LoggerFactory




@Serializable
data class Messages( val name:String ="", val link:String ="",
                     val desc:String ="", val time:Long = 0)


val id = System.getenv("DB_USER")
val baseURL:String = id


@Serializable
data class Projects (val imgSrc:String="", val title:String = "", val desc:String="",
                     val fullDescription:List<String> =listOf(), val tools:List<String> =listOf(),
                     val gitHubLink : String = "", val contentLink:String="",
                     val images:List<String> = listOf()
)



val logger = LoggerFactory.getLogger("\t\tPortfolio Server Log ")



object DBAccess{
    private val client = MongoClient.create(baseURL)
//    private val code = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), fromProviders(
//        PojoCodecProvider.builder().automatic(true).build()));
    private val db = client.getDatabase("Portfolio")//.withCodecRegistry(code)


    fun getProjects(): FindFlow<Projects> {
        val collection = db.getCollection<Projects>("Projects")

        return collection.find()
    }

    fun addMsg(msg: Messages){
        val msgCollection = db.getCollection<Messages>("Messages")
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            try{
                msgCollection.insertOne(msg)
            }catch(err:Exception){
                logger.info("{}",err.stackTrace)
            }
        }
    }
}
