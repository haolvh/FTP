package client.command;

import client.gui.ClientFrame;
import client.gui.msg.MessageDialog;
import client.gui.panel.ServerFilePanel;
import client.mode.InitiativeMode;
import client.mode.Mode;
import client.mode.PassiveMode;
import entity.ConnectType;
import entity.Protocol;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author LvHao
 * @Description :处理客户端命令接受的线程
 * 目前客户端是用监听器来实现多线程逻辑
 * 收发命令在一起
 * 不断监听服务端命令和数据
 * @date 2020-07-04 18:11
 */
@Slf4j(topic = "showServerDir")


@RequiredArgsConstructor
public class ClientCommandHandler implements Runnable {
    private static ServerFilePanel serverFilePanel;
    private static DefaultTableModel model;
    private static Socket socket;
    private static Mode mode;
    private static ArrayList<String[]> receiveData = new ArrayList<>();
    private static ArrayList<String[]> sendData = new ArrayList<>();

    @NonNull
    private ClientFrame clientFrame;

    @NonNull
    private ObjectInputStream objectInputStream;

    @Override
    public void run() {
        socket = clientFrame.getSocket();
        serverFilePanel = clientFrame.getJPanel3().getJPanel2();
        model = clientFrame.getJPanel3().getJPanel2().getModel();

        try {
            //读入服务端命令的协议
            Protocol protocolFromSocket = (Protocol) objectInputStream.readObject();
            log.info(protocolFromSocket.toString());
            while(objectInputStream.readObject()!=null) {
                socket.shutdownInput();
            }
            //如果是主动模式
            if(ConnectType.INITIATIVE.equals(protocolFromSocket.getConnectType())){
                mode = new InitiativeMode();
            }else {
                //被动模式
                mode = new PassiveMode();
                clientFrame.PsvdataSocket = new Socket(protocolFromSocket.getServiceIp(), protocolFromSocket.getDataPort());
            }
            while (true){
                //下面是对各种操作的处理
                switch (protocolFromSocket.getOperateType()){
                    case FILE_PATH:
                    case RETURN_FATHER_DIR:
                    case CONNECT:{
                        mode.showServerDir(protocolFromSocket,serverFilePanel,model,clientFrame);
                        break;
                    }
                    case PAUSE:{
                        mode.pause();
                        break;
                    }
                    case DOWNLOAD:{
                        mode.download(protocolFromSocket,clientFrame,receiveData);
                        break;
                    }
                    case UPLOAD:{
                        mode.upload(protocolFromSocket,clientFrame,sendData);
                        break;
                    }
                    case ERROR:{
                        mode.error(clientFrame);
                        break;
                    }
                    case DELETE:{
                        mode.delete(protocolFromSocket,clientFrame);
                        break;
                    }
                }
                protocolFromSocket = (Protocol) objectInputStream.readObject();
                while(objectInputStream.readObject()!=null) {
                    socket.shutdownInput();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            new MessageDialog("提示","连接已断开！",clientFrame).init();
        }
    }
}