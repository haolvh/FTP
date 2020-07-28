package client.command;

import client.gui.ClientFrame;
import client.gui.panel.ServerFilePanel;
import client.mode.InitiativeMode;
import client.mode.Mode;
import client.mode.PassiveMode;
import entity.ConnectType;
import entity.Protocol;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author LvHao
 * @Description : 客户端单个处理处理服务器命令
 * 测试功能时使用
 * @date 2020-07-05 8:16
 */
public class ReceiveCommand {

    private static ServerFilePanel serverFilePanel;
    private static DefaultTableModel model;
    private static Socket socket;
    private static Mode mode;
    private static ArrayList<String[]> data = new ArrayList<>();

    public static void receiveCommand(ClientFrame clientFrame, ObjectInputStream objectInputStream){
        socket = clientFrame.getSocket();
        serverFilePanel = clientFrame.getJPanel3().getJPanel2();
        model = clientFrame.getJPanel3().getJPanel2().getModel();
        try {
            //读入服务端命令的协议
            Protocol protocolFromSocket = (Protocol) objectInputStream.readObject();
            while(objectInputStream.readObject()!=null) {
                socket.shutdownInput();
            }
            //如果是主动模式
            if(ConnectType.INITIATIVE.equals(protocolFromSocket.getConnectType())){
                mode = new InitiativeMode();
            }else {
                //被动模式
                mode = new PassiveMode();
            }
            //下面是对各种操作的处理
            switch (protocolFromSocket.getOperateType()){
                case FILE_PATH:
                case RETURN_FATHER_DIR:
                case CONNECT:{
                    mode.showServerDir(protocolFromSocket,serverFilePanel,model,clientFrame);
                }
                case PAUSE:{
                    mode.pause();
                }
                case DOWNLOAD:{
                    mode.download(protocolFromSocket,clientFrame,data);
                }
                case UPLOAD:{
                    mode.upload(protocolFromSocket,clientFrame,data);
                }
                case DELETE:{
                    mode.delete(protocolFromSocket,clientFrame);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
