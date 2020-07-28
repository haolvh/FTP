package client.socket;

import client.gui.ClientFrame;
import client.gui.msg.MessageDialog;
import entity.Protocol;
import lombok.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * @author LvHao
 * @Description :  客户端连接到服务器的socket设置
 * @date 2020-07-03 10:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocketUtil {

    Protocol protocol;

    private ClientFrame clientFrame;

    public java.net.Socket createSocket(){
        java.net.Socket socket = null;
        try{
            socket = new java.net.Socket();
            SocketAddress socketAddress = new InetSocketAddress(protocol.getServiceIp(),protocol.getCommandPort());
            socket.connect(socketAddress,1000000000);
            socket.setSoTimeout(1000000000);
        } catch (IOException e) {
            socket = null;
            new MessageDialog("连接状态","连接失败",clientFrame).init();
        }
        return socket;
    }
}
