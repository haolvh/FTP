package client;

import client.gui.ClientFrame;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 * @author LvHao
 * @Description : 启动客户端GUI界面
 * @date 2020-07-02 12:27
 */
public class RunClient {
    public static void main(String[] args) {
        //设置GUI的模式
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        ClientFrame clientFrame = new ClientFrame();
        clientFrame.setVisible(true);
    }
}
