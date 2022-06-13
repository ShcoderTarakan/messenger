package event.input
import kotlinx.serialization.*

@Serializable
data class RegisterEvent(val login: String, val name: String): InputEvent
