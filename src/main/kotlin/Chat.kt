import event.input.*
import handlers.*
import model.*
import repositories.ConnectionsRepository

object Chat {
    fun addConnection(connection: Connection) {
        ConnectionsRepository.addConnection(connection)
        connection.client.eventHandler = { event ->
            when (event) {
                is AuthEvent -> AuthHandler(event).handle(connection)
                is RegisterEvent -> RegisterHandler(event).handle(connection)
                is OpenDirectChatEvent -> OpenDirectChatHandler(event).handle(connection)
                is DirectMessageSendEvent -> DirectMessageHandler(event).handle(connection)
                is GlobalMessageSendEvent -> GlobalMessageHandler(event).handle(connection)
                is LogOutEvent -> LogOutHandler().handle(connection)
                is DisconnectEvent -> ConnectionsRepository.removeConnection(connection)
            }
        }
    }
}
