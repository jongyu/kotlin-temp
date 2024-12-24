import com.fazecast.jSerialComm.SerialPort;

public class CH340Reader {

    public static void main(String[] args) {
        // 获取可用的串口列表
        SerialPort[] ports = SerialPort.getCommPorts();

        // 选择要使用的串口
        SerialPort port = ports[0];

        // 设置串口参数
        port.setBaudRate(4800);
        port.setNumDataBits(8);
        port.setNumStopBits(1);
        port.setParity(SerialPort.NO_PARITY);

        // 打开串口
        if (port.openPort()) {
            System.out.println("串口已打开");

            // 创建一个字节缓冲区
            byte[] buffer = new byte[1024];

            // 读取串口数据
            while (true) {
                int bytesRead = port.readBytes(buffer, buffer.length);
                System.out.println("读取到" + bytesRead + "字节数据");

                // 处理数据
                String data = new String(buffer, 0, bytesRead);
                System.out.println("数据内容：" + data);
            }

            // 关闭串口
        } else {
            System.out.println("无法打开串口");
        }
    }

}
