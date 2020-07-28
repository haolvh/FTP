package entity;

import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 产生一个随机端口
 *
 * @author yinchao
 * @date 2020/7/4 22:56
 */
@NoArgsConstructor
public class GenerateDataSocket {
    public Socket generateInPassiveMode(Integer port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        Socket dataTransportSocket = serverSocket.accept();
        return dataTransportSocket;
    }
}
