package client.socket;

import client.command.ClientCommandHandler;
import client.command.ReceiveCommand;
import client.command.SendCommand;
import client.gui.ClientFrame;
import entity.Protocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author LvHao
 * @Description : 初始化一个客户端到服务端的连接
 * 发送主动模式 并开始命令接受线程
 * @date 2020-07-03 10:59
 */
public class ConnectServer extends SocketUtil {
    public ConnectServer(Protocol protocol, ClientFrame clientFrame) {
        super(protocol,clientFrame);
        clientFrame.setSocket(this.createSocket());
        clientFrame.setProtocol(protocol);
        try {
            clientFrame.setSocketObjectInputStream(new ObjectInputStream(clientFrame.getSocket().getInputStream()));
            clientFrame.setSocketObjectOutputStream(new ObjectOutputStream(clientFrame.getSocket().getOutputStream()));
            SendCommand.sendCommend(protocol,clientFrame.getSocket(),clientFrame.getSocketObjectOutputStream());
            new Thread(new ClientCommandHandler(clientFrame,clientFrame.getSocketObjectInputStream())).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(new IsConnect(clientFrame)).start();
    }
}
