package model

data class Connection(
    val client: Client,
    var user: User? = null,
)
