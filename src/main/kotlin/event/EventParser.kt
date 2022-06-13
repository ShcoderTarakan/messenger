package event

import event.input.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*

object EventParser {
    private val format = Json { ignoreUnknownKeys = true }

    fun parse(data: String): InputEvent {
        val eventType = format.decodeFromString<EventType>(data)

        return when (eventType.type) {
            "AUTH" -> format.decodeFromString<AuthEvent>(data)
            "REGISTER" -> format.decodeFromString<RegisterEvent>(data)
            "OPEN_DIRECT_CHAT" -> format.decodeFromString<OpenDirectChatEvent>(data)
            "DIRECT_MESSAGE_SEND" -> format.decodeFromString<DirectMessageSendEvent>(data)
            "GLOBAL_MESSAGE_SEND" -> format.decodeFromString<GlobalMessageSendEvent>(data)
            "DISCONNECT" -> DisconnectEvent()
            "LOG_OUT" -> LogOutEvent()
            else -> EmptyEvent()
        }
    }
}
