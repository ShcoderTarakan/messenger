import event.EventParser
import model.Client
import event.input.InputEvent
import websocket.ClientReader
import websocket.ClientWriter
import java.net.Socket
import java.security.MessageDigest
import java.util.*
import java.util.regex.Pattern

private const val KEY_APPENDIX = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11"
private const val OUTPUT_HEADERS = "HTTP/1.1 101 Switching Protocols\r\n" +
        "Upgrade: websocket\r\n" +
        "Connection: Upgrade\r\n" +
        "Sec-WebSocket-Accept:"

class WebsocketClientHandler(socket: Socket) : Client {
    private val input = socket.getInputStream()
    private val output = socket.getOutputStream()

    private val clientReader = ClientReader(input, ::clientEvent)
    private val clientWriter = ClientWriter(output)
    override var eventHandler: ((InputEvent) -> Unit)? = null

    init {
        doHandshake()
        clientReader.start()
    }

    override fun send(data: String) {
        clientWriter.write(data)
    }

    private fun clientEvent(message: String) {
        val event = EventParser.parse(message)

        if (eventHandler != null) {
            eventHandler!!(event)
        }
    }

    private fun doHandshake() {
        val data = Scanner(input, "UTF-8").useDelimiter("\\r\\n\\r\\n").next()
        if (data.startsWith("GET")) {
            val match = Pattern.compile("Sec-WebSocket-Key: (.*)").matcher(data)
            match.find()

            val response = "$OUTPUT_HEADERS ${getAcceptKey(match.group(1))}\r\n\r\n".toByteArray(charset("UTF-8"))

            output.write(response, 0, response.size)
        }
    }

    private fun getAcceptKey(key: String): String {
        val sha1 = MessageDigest.getInstance("SHA-1").digest("$key$KEY_APPENDIX".toByteArray(charset("UTF-8")))
        return Base64.getEncoder().encodeToString(sha1)
    }
}

