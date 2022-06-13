package handlers

import event.OutputEvent
import event.input.AuthEvent
import model.Connection
import repositories.ConnectionsRepository
import repositories.MessagesRepository
import repositories.UsersRepository

class AuthHandler(private val event: AuthEvent): Handler {
    override fun handle(connection: Connection) {
        val user = UsersRepository.findUser(event.login)
        connection.user = user

        if (user == null) {
            ConnectionsRepository.sendEvent(connection, OutputEvent.AuthError())
        } else {
            ConnectionsRepository.sendEvent(connection, OutputEvent.AuthSuccess(user))

            val users = UsersRepository.getAllUsers()
            val messages = MessagesRepository.getGlobalMessages()

            ConnectionsRepository.sendEvent(connection, OutputEvent.UserList(users))
            ConnectionsRepository.sendEvent(connection, OutputEvent.GlobalMessagesList(messages))
        }
    }
}
