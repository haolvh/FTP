package client.gui.action;

import client.gui.ClientFrame;
import client.gui.panel.DefaultInfoPanel;
import client.socket.ConnectServer;
import client.util.IPUtil;
import entity.ConnectType;
import entity.OperateType;
import entity.Protocol;
import entity.User;
import lombok.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author LvHao
 * @Description : 对连接按钮的事件监听 区分主动模式和被动模式
 * 主动模式打开命令端口
 * 被动模式还应该获取服务端送来的数据端口的相关信息
 * @date 2020-07-03 11:16
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class ConnectToServer implements ActionListener {
    private ConnectType type;

    @NonNull
    private JComboBox jComboBox;
    @NonNull
    private DefaultInfoPanel defaultInfoPanel;
    @NonNull
    private ClientFrame clientFrame;

    @SneakyThrows
    @Override
    public void actionPerformed(ActionEvent e) {
        if(jComboBox.getSelectedItem().equals("主动模式")){
            type = ConnectType.INITIATIVE;
        }else{
            type = ConnectType.PASSIVE;
        }

        User user = new User();
        user.setUserName(defaultInfoPanel.getJt2().getText());
        user.setPassword(defaultInfoPanel.getJPasswordField().getText());

        Protocol protocol = new Protocol();
        protocol.setServiceIp(defaultInfoPanel.getJt1().getText());
        protocol.setCommandPort(Integer.valueOf(defaultInfoPanel.getJt3().getText()));
        protocol.setClientIp(IPUtil.getLocalIP());
        protocol.setConnectType(type);
        protocol.setOperateType(OperateType.CONNECT);
        protocol.setData(user);
        protocol.setDataPort(Integer.valueOf(defaultInfoPanel.getJt3().getText()));

        //主动模式的第一次连接
        if(jComboBox.getSelectedItem().equals("主动模式")){
            new ConnectServer(protocol,clientFrame);
            if(clientFrame.getSocket() != null && clientFrame.getSocket().isConnected()){
                jComboBox.setEnabled(false);
                defaultInfoPanel.getJt1().setEditable(false);
                defaultInfoPanel.getJt2().setEditable(false);
                defaultInfoPanel.getJt3().setEditable(false);
                defaultInfoPanel.getJPasswordField().setEditable(false);
                defaultInfoPanel.remove(defaultInfoPanel.getJButton());
                defaultInfoPanel.add(defaultInfoPanel.getJLabel());
                defaultInfoPanel.updateUI();
            }
        }else{
            //被动模式的第一次连接
            //TODO something
            new ConnectServer(protocol,clientFrame);
            if(clientFrame.getSocket() != null && clientFrame.getSocket().isConnected()){
                jComboBox.setEnabled(false);
                defaultInfoPanel.getJt1().setEditable(false);
                defaultInfoPanel.getJt2().setEditable(false);
                defaultInfoPanel.getJt3().setEditable(false);
                defaultInfoPanel.getJPasswordField().setEditable(false);
                defaultInfoPanel.remove(defaultInfoPanel.getJButton());
                defaultInfoPanel.add(defaultInfoPanel.getJLabel());
                defaultInfoPanel.updateUI();
            }
        }
    }
}
