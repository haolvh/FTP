package client.gui.action;

import client.gui.panel.NewClientPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author LvHao
 * @Description :新建一个连接  弹出一个对话窗口
 *                  还需完善
 * @date 2020-07-03 1:52
 */
public class NewConnector implements ActionListener {
    private final int X = 300;
    private final int Y = 200;

    @Override
    public void actionPerformed(ActionEvent e) {
        JDialog jDialog = new NewClientPanel();
        jDialog.setTitle("新建连接");
        jDialog.setSize(600,800);
        /**
         * 窗口阻塞
         */
        jDialog.setSize(X,Y);
        jDialog.setLocationRelativeTo(null);
        jDialog.setVisible(true);
    }
}
