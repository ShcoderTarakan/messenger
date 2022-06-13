package handlers

import event.OutputEvent
import event.input.RegisterEvent
import model.Connection
import repositories.ConnectionsRepository
import repositories.MessagesRepository
import repositories.UsersRepository

class RegisterHandler(private val event: RegisterEvent) : Handler {
    override fun handle(connection: Connection) {
        var user = UsersRepository.findUser(event.login)
        if (user != null) {
            return ConnectionsRepository.sendEvent(connection, OutputEvent.RegisterError("User exists"))
        }

        user = UsersRepository.createUser(login = event.login, name = event.name)
        connection.user = user

        val users = UsersRepository.getAllUsers()
        val messages = MessagesRepository.getGlobalMessages()

        ConnectionsRepository.broadcastEvent(OutputEvent.NewUser(user))
        ConnectionsRepository.sendEvent(connection, OutputEvent.AuthSuccess(user))
        ConnectionsRepository.sendEvent(connection, OutputEvent.UserList(users))
        ConnectionsRepository.sendEvent(connection, OutputEvent.GlobalMessagesList(messages))
    }
}
