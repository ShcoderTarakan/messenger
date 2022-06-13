package model

import kotlinx.serialization.Serializable

@Serializable
data class GlobalMessage(
    val id: Int,
    val userId: Int,
    val text: String,
    val createdAt: String
)
