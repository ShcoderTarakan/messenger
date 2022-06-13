package event

import kotlinx.serialization.Serializable
import model.User

@Serializable
sealed class OutputEvent {
    abstract val type: String

    @Serializable
    data class AuthSuccess(val user: User): OutputEvent() {
        override val type = "AUTH_SUCCESS"
    }

    @Serializable
    data class DirectMessage(val message: model.DirectMessage): OutputEvent() {
        override val type = "DIRECT_MESSAGE"
    }

    @Serializable
    data class DirectMessagesList(val messages: List<model.DirectMessage>): OutputEvent() {
        override val type = "DIRECT_MESSAGES_LIST"
    }

    @Serializable
    data class GlobalMessage(val message: model.GlobalMessage): OutputEvent() {
        override val type = "GLOBAL_MESSAGE"
    }

    @Serializable
    data class GlobalMessagesList(val data: List<model.GlobalMessage>): OutputEvent() {
        override val type = "GLOBAL_MESSAGES_LIST"
    }

    @Serializable
    class AuthError: OutputEvent() {
        override val type = "AUTH_ERROR"
    }

    @Serializable
    class LogOut: OutputEvent() {
        override val type = "LOG_OUT_SUCCESS"
    }

    @Serializable
    data class NewUser(val user: User): OutputEvent() {
        override val type = "NEW_USER"
    }

    @Serializable
    data class RegisterError(val message: String): OutputEvent() {
        override val type = "REGISTER_ERROR"
    }

    @Serializable
    data class UserList(val users: List<User>): OutputEvent() {
        override val type = "USER_LIST"
    }
}
