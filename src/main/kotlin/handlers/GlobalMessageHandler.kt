package handlers

import event.OutputEvent
import event.input.GlobalMessageSendEvent
import model.Connection
import repositories.ConnectionsRepository
import repositories.MessagesRepository

class GlobalMessageHandler(private val event: GlobalMessageSendEvent): Handler {
    override fun handle(connection: Connection) {
        val message = MessagesRepository.createGlobalMessage(
            userId = connection.user?.id ?: 0,
            text =  event.text,
        )

        ConnectionsRepository.broadcastEvent(OutputEvent.GlobalMessage(message))
    }
}
