package handlers

import model.Connection

interface Handler {
    fun handle(connection: Connection)
}
