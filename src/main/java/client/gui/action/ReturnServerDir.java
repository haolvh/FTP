package client.gui.action;

import client.command.ReceiveCommand;
import client.command.SendCommand;
import client.gui.ClientFrame;
import client.gui.msg.MessageDialog;
import client.gui.panel.ServerFilePanel;
import client.util.IPUtil;
import entity.ConnectType;
import entity.FileModel;
import entity.OperateType;
import entity.Protocol;
import lombok.AllArgsConstructor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @author LvHao
 * @Description : 遍历服务端问价夹上一级的监听
 * @date 2020-07-05 18:19
 */
@AllArgsConstructor
public class ReturnServerDir implements ActionListener {

    private ClientFrame clientFrame;

    private ServerFilePanel serverFilePanel;

    @Override
    public void actionPerformed(ActionEvent e) {
        if(clientFrame.getSocket() != null && clientFrame.getSocket().isConnected()){
            FileModel fileModel = new FileModel();
            String fileName = serverFilePanel.getJTextField().getText()+(serverFilePanel.getJTable().size().getHeight()==0? "":serverFilePanel.getJTable().getValueAt(0,0).toString());
            fileModel.setFilePath(fileName);
            Protocol protocol = new Protocol();
            protocol.setServiceIp(clientFrame.getProtocol().getServiceIp());
            protocol.setCommandPort(clientFrame.getProtocol().getCommandPort());
            protocol.setClientIp(IPUtil.getLocalIP());
            protocol.setConnectType(ConnectType.INITIATIVE);
            protocol.setOperateType(OperateType.RETURN_FATHER_DIR);
            protocol.setData(fileModel);
            protocol.setDataPort(clientFrame.getProtocol().getDataPort());


            SendCommand.sendCommend(protocol,clientFrame.getSocket(),clientFrame.getSocketObjectOutputStream());
        }else{
            new MessageDialog("提示","请先连接服务器！",clientFrame).init();
        }
    }
}
