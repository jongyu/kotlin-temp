package org.example

import com.fazecast.jSerialComm.SerialPort
import java.nio.charset.Charset

fun main() {
    val portName = "/dev/tty.wchusbserial830" // 替换为正确的串口设备
    val baudRate = 9600

    val commPort = SerialPort.getCommPort(portName)
    commPort.setComPortParameters(baudRate, 8, 1, SerialPort.NO_PARITY)
//    commPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0)

    if (commPort.openPort()) {
        println("Port opened successfully.")

        while (true) {
            if (commPort.bytesAvailable() > 0) {
                val buffer = ByteArray(commPort.bytesAvailable())
                val numRead = commPort.readBytes(buffer, buffer.size)

                // 尝试使用 UTF-8 和 GBK 解码
                val dataUtf8 = String(buffer, 0, numRead, Charsets.UTF_8)
                val dataGbk = String(buffer, 0, numRead, Charset.forName("GBK"))

                // 简单检查是否包含乱码
                val isUtf8Valid = !dataUtf8.contains("�") // 替换为更严格的检查逻辑
                val isGbkValid = !dataGbk.contains("�")

                val data = if (isUtf8Valid) dataUtf8 else if (isGbkValid) dataGbk else "无法解码"
                println(data)
            }

            Thread.sleep(1010) // 每秒读取一次
        }

        commPort.closePort()
        println("Port closed.")
    } else {
        println("Failed to open port.")
    }
}