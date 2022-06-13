package model

import kotlinx.serialization.Serializable

@Serializable
data class DirectMessage(
    val id: Int,
    val senderId: Int,
    val recipientId: Int,
    val text: String,
    val createdAt: String
)
