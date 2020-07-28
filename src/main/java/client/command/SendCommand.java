package client.command;

import entity.Protocol;

import java.io.*;
import java.net.Socket;

/**
 * @author LvHao
 * @Description : 命令发送的实现
 * 每次写完协议只有输出流里在写一个null
 * 方便服务端判断协议的加载是否已完成
 * @date 2020-07-03 16:05
 */
public class SendCommand{
    public static void sendCommend(Protocol protocol,Socket socket,ObjectOutputStream objectOutputStream){
        try{
            if(socket != null && socket.isConnected()){
                objectOutputStream.writeObject(protocol);
                objectOutputStream.writeObject(null);
                objectOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
