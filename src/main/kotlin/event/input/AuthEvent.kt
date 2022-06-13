package event.input
import kotlinx.serialization.*

@Serializable
data class AuthEvent(val login: String): InputEvent
