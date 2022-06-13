package websocket

import java.io.InputStream

class ClientReader(
    private val input: InputStream,
    private var eventHandler: (String) -> Unit,
): Thread() {
    override fun run() {
        printInputStream()
    }

    private fun disconnect() {
        eventHandler("{ \"type\": \"DISCONNECT\" }")
    }

    private fun printInputStream() {
        var len = 0
        var b = ByteArray(1024)
        while (true) {
            len = input.read(b)
            if (len != -1) {
                var rLength: Byte = 0
                var rMaskIndex = 2
                var rDataStart = 0
                val opCode = b[0].toInt() and 15

                if (opCode == 8) {
                    return disconnect()
                }

                val data = b[1]
                val op = 127.toByte()
                rLength = (data.toInt() and op.toInt()).toByte()
                if (rLength == 126.toByte()) rMaskIndex = 4
                if (rLength == 127.toByte()) rMaskIndex = 10
                val masks = ByteArray(4)
                var j = 0
                var i = 0
                i = rMaskIndex
                while (i < rMaskIndex + 4) {
                    masks[j] = b[i]
                    j++
                    i++
                }
                rDataStart = rMaskIndex + 4
                val messLen = len - rDataStart
                val message = ByteArray(messLen)
                i = rDataStart
                j = 0
                while (i < len) {
                    message[j] = (b[i].toInt() xor masks[j % 4].toInt()).toByte()
                    i++
                    j++
                }
                eventHandler(message.decodeToString())
                b = ByteArray(1024)
            } else {
                disconnect()
                return
            }
        }
    }
}