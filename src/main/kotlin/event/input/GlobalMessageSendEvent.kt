package event.input

import kotlinx.serialization.Serializable

@Serializable
data class GlobalMessageSendEvent(val text: String): InputEvent
