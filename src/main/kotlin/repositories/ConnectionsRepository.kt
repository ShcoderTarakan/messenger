package repositories

import event.OutputEvent
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import model.Connection

object ConnectionsRepository {
    private val connections = mutableListOf<Connection>()
    private val format = Json {
        encodeDefaults = true
        classDiscriminator = "class"
    }

    fun sendEvent(connection: Connection, event: OutputEvent) {
        val data = format.encodeToString(event)

        try {
            connection.client.send(data)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun broadcastEvent(event: OutputEvent) {
        connections.forEach {
            sendEvent(it, event)
        }
    }

    fun addConnection(connection: Connection) {
        connections.add(connection)
    }

    fun removeConnection(connection: Connection) {
        connections.remove(connection)
    }

    fun getUserConnection(userId: Int): Connection? {
        return connections.find { it.user?.id == userId }
    }
}