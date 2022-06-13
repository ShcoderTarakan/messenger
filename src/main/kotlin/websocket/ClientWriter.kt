package websocket

import java.io.OutputStream

class ClientWriter(private val output: OutputStream) {
    fun write(data: String) {
        output.write(encode(data))
    }

    private fun encode(mess: String): ByteArray {
        val rawData = mess.toByteArray()
        var frameCount = 0
        val frame = ByteArray(10)
        frame[0] = 129.toByte()
        if (rawData.size <= 125) {
            frame[1] = rawData.size.toByte()
            frameCount = 2
        } else if (rawData.size in 126..65535) {
            frame[1] = 126.toByte()
            val len = rawData.size
            frame[2] = (len shr 8 and 255.toByte().toInt()).toByte()
            frame[3] = (len and 255.toByte().toInt()).toByte()
            frameCount = 4
        } else {
            frame[1] = 127.toByte()
            val len = rawData.size
            frame[2] = (len shr 56 and 255.toByte().toInt()).toByte()
            frame[3] = (len shr 48 and 255.toByte().toInt()).toByte()
            frame[4] = (len shr 40 and 255.toByte().toInt()).toByte()
            frame[5] = (len shr 32 and 255.toByte().toInt()).toByte()
            frame[6] = (len shr 24 and 255.toByte().toInt()).toByte()
            frame[7] = (len shr 16 and 255.toByte().toInt()).toByte()
            frame[8] = (len shr 8 and 255.toByte().toInt()).toByte()
            frame[9] = (len and 255.toByte().toInt()).toByte()
            frameCount = 10
        }
        val bLength = frameCount + rawData.size
        val reply = ByteArray(bLength)
        var bLim = 0
        for (i in 0 until frameCount) {
            reply[bLim] = frame[i]
            bLim++
        }
        for (i in rawData.indices) {
            reply[bLim] = rawData[i]
            bLim++
        }
        return reply
    }
}
