package client.gui.action;

import client.command.ReceiveCommand;
import client.command.SendCommand;
import client.gui.ClientFrame;
import client.gui.msg.MessageDialog;
import client.util.IPUtil;
import configuration_and_constant.Constant;
import entity.OperateType;
import entity.Protocol;
import entity.User;
import lombok.AllArgsConstructor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author LvHao
 * @Description : 遍历服务端初始文件列表
 * @date 2020-07-05 18:10
 */
@AllArgsConstructor
public class GetServerDir implements ActionListener {

    private ClientFrame clientFrame;

    @Override
    public void actionPerformed(ActionEvent e) {
        if(clientFrame.getSocket() != null &&clientFrame.getSocket().isConnected()){

            User user = new User();
            user.setUserName(clientFrame.getJPanel2().getJt2().getText());
            user.setPassword(clientFrame.getJPanel2().getJPasswordField().getText());

            Protocol protocol = new Protocol();
            protocol.setServiceIp(clientFrame.getProtocol().getServiceIp());
            protocol.setCommandPort(clientFrame.getProtocol().getCommandPort());
            protocol.setClientIp(IPUtil.getLocalIP());
            if(clientFrame .PsvdataSocket != null){
                if(clientFrame.PsvdataSocket.isConnected()){
                    protocol.setOperateType(OperateType.FILE_PATH);
                    protocol.setData(Constant.DEFAULT_FILE_PATH);
                }
            }else{
                protocol.setOperateType(OperateType.CONNECT);
                protocol.setData(user);
            }
            protocol.setConnectType(clientFrame.getProtocol().getConnectType());
            protocol.setDataPort(clientFrame.getProtocol().getDataPort());

            SendCommand.sendCommend(protocol,clientFrame.getSocket(),clientFrame.getSocketObjectOutputStream());
        }else{
            new MessageDialog("提示","请先连接服务器！",clientFrame).init();
        }
    }
}