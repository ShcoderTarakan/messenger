import db.dbConnect
import model.Connection
import java.net.ServerSocket

fun main() {
    dbConnect()

    val server = ServerSocket(3000)
    println("Server started on :3000")

    while (true) {
        val socket = server.accept()
        val client = WebsocketClientHandler(socket)

        Chat.addConnection(Connection(client = client))
    }
}
