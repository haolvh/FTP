package client.gui.panel;

import client.gui.action.NewConnector;
import client.gui.action.StopConnect;
import client.gui.MyGridBagConstraints;
import client.gui.ClientFrame;

import javax.swing.*;
import java.awt.*;

/**
 * @author LvHao
 * @Description : 顶部新建连接和断开连接的面板
 * @date 2020-07-03 1:50
 */
public class TopPanel extends JPanel {
    public TopPanel(ClientFrame clientFrame,DefaultInfoPanel jPanel){
        init(clientFrame,jPanel);
    }

    private void init(ClientFrame clientFrame,DefaultInfoPanel jPanel){
        /**
         * 设置最上面的那两个按钮
         */
        JButton jButton1 = new JButton("新建连接");
        jButton1.addActionListener(new NewConnector());
        JButton jButton2 = new JButton("断开连接");
        jButton2.addActionListener(new StopConnect(clientFrame,jPanel));

        //设置布局方式
        setLayout(new GridBagLayout());

        //新建连接button位置
        add(jButton1,new MyGridBagConstraints(0,0,1,1).init2());

        //断开连接button位置
        add(jButton2,new MyGridBagConstraints(1,0,1,1).init2());
    }
}
