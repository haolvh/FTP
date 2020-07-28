package server;

import lombok.Data;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


/**
 * @类名 Port
 * @描述 用于实现FTP主动模式
 * @作者 heguicai
 * @创建日期 2020/7/2 下午3:26
 **/
public class Port {
    static Socket dataPort;

    /**
     *
     * @param address
     * @param port
     * @return
     * @throws IOException
     * 主动模式获取数据传输借口
     */
    public static Socket getDataPort(String address, Integer port) throws IOException {
        dataPort = new Socket(address, port);
        return dataPort;
    }

    /**
     *
     * @param socket
     * @return
     * @throws IOException
     * 获取主动模式数据传接口的getDataOutputStream
     */
    public static DataOutputStream getDataOutputStream(Socket socket) throws IOException {
        DataOutputStream das=new DataOutputStream(socket.getOutputStream());
        return  das;
    }
}