package event.input

import kotlinx.serialization.Serializable

@Serializable
data class OpenDirectChatEvent(val userId: Int) : InputEvent
