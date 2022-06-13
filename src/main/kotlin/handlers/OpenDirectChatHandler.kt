package handlers

import event.OutputEvent
import event.input.OpenDirectChatEvent
import model.Connection
import repositories.ConnectionsRepository
import repositories.MessagesRepository

class OpenDirectChatHandler(private val event: OpenDirectChatEvent) : Handler {
    override fun handle(connection: Connection) {
        val messages = MessagesRepository.getAllDirectMessages(connection.user?.id ?: 0, event.userId)

        ConnectionsRepository.sendEvent(
            connection,
            OutputEvent.DirectMessagesList(messages)
        )
    }
}