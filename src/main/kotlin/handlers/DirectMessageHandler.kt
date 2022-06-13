package handlers

import event.OutputEvent
import event.input.DirectMessageSendEvent
import model.Connection
import repositories.ConnectionsRepository
import repositories.MessagesRepository

class DirectMessageHandler(private val event: DirectMessageSendEvent): Handler {
    override fun handle(connection: Connection) {
        val message = MessagesRepository.createDirectMessage(
            senderId = connection.user?.id ?: 0,
            recipientId = event.userId,
            text =  event.text,
        )

        ConnectionsRepository.sendEvent(connection, OutputEvent.DirectMessage(message))

        val recipient = ConnectionsRepository.getUserConnection(event.userId)

        if (recipient != null) {
            ConnectionsRepository.sendEvent(recipient, OutputEvent.DirectMessage(message))
        }
    }
}
