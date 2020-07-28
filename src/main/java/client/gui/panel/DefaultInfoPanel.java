package client.gui.panel;

import client.gui.action.ConnectToServer;
import client.gui.ClientFrame;
import lombok.Data;

import javax.swing.*;
import java.awt.*;

/**
 * @author LvHao
 * @Description : 客户端初始的默认连接信息
 * @date 2020-07-03 1:54
 */
@Data
public class DefaultInfoPanel extends JPanel {

    private final String ip = "127.0.0.1";
    private final String name = "root";
    private final String password = "root";
    private final String port = "8081";

    private JTextField jt1 = new JTextField(ip, 16);
    private JTextField jt2 = new JTextField(name, 15);
    private JTextField jt3 = new JTextField(port, 10);
    private JButton jButton = new JButton("连接");
    private JLabel jLabel = new JLabel(" 已连接!");
    private JComboBox jComboBox = new JComboBox();
    private JPasswordField jPasswordField = new JPasswordField(password,10);


    public DefaultInfoPanel(ClientFrame clientFrame){
        init(clientFrame);
    }

    /**
     * 设置初始的默认连接信息
     * 主机：127.0.0.1
     * 用户名：root
     * 密码：123456
     * 端口：20
     */
    private void init(ClientFrame clientFrame){
        JLabel j1 = new JLabel("主机地址:");
        JLabel j2 = new JLabel("用户名：");
        JLabel j3 = new JLabel("密码：");
        JLabel j4 = new JLabel("端口：");
        jComboBox.addItem("主动模式");
        jComboBox.addItem("被动模式");

        setLayout(new FlowLayout());
        add(j1);
        add(jt1);
        add(j2);
        add(jt2);
        add(j3);
        add(jPasswordField);
        add(j4);
        add(jt3);
        add(jComboBox);
        add(jButton);

        jButton.addActionListener(new ConnectToServer(jComboBox,this,clientFrame));
    }
}

