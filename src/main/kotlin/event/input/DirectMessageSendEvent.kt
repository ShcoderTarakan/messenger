package event.input

import kotlinx.serialization.Serializable

@Serializable
data class DirectMessageSendEvent(val userId: Int, val text: String): InputEvent
