package handlers

import event.OutputEvent
import model.Connection
import repositories.ConnectionsRepository

class LogOutHandler : Handler {
    override fun handle(connection: Connection) {
        connection.user = null
        ConnectionsRepository.sendEvent(connection, OutputEvent.LogOut())
    }
}