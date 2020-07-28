package client.gui.panel;


import client.gui.ClientFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author 13378
 * @Description:新建连接按钮
 * @data:2020-07-03-10:27
 **/
public class NewClientPanel extends JDialog{
    private final String ip = "127.0.0.1";
    private final String name = "root";
    private final String password = "123456";
    private final String port = "20";
    public NewClientPanel(){init();}
    private void init(){
        this.setDefaultCloseOperation(2);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        JLabel j1 = new JLabel("主机地址:");
        JLabel j2 = new JLabel("用户名：");
        JLabel j3 = new JLabel("密码：");
        JLabel j4 = new JLabel("端口：");
        JLabel j5 = new JLabel("连接一个新的FTP");

        JTextField jt1 = new JTextField(ip,15);
        JTextField jt2 = new JTextField(name,15);
        JTextField jt3 = new JTextField(port,15);

        j1.setHorizontalAlignment(SwingConstants.RIGHT);
        j2.setHorizontalAlignment(SwingConstants.RIGHT);
        j3.setHorizontalAlignment(SwingConstants.RIGHT);
        j4.setHorizontalAlignment(SwingConstants.RIGHT);

        JPasswordField jPasswordField = new JPasswordField(password,15);
        JButton jButton1 = new JButton("连接");
        jButton1.setHorizontalAlignment(SwingConstants.CENTER);
        jButton1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientFrame clientFrame = new ClientFrame();
                clientFrame.setVisible(true);
            }
        });

        setLayout(new GridLayout(5,2));
        add(j1);
        add(jt1);
        add(j2);
        add(jt2);
        add(j3);
        add(jPasswordField);
        add(j4);
        add(jt3);
        add(jButton1);
        add(j5);
    }
}
