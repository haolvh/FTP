package client.gui.action;

import client.gui.ClientFrame;
import client.gui.msg.MessageDialog;
import client.gui.panel.DefaultInfoPanel;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author LvHao
 * @Description : 关闭连接按钮的相关动作
 * 断开数据端口
 * 断开命令端口
 * 将他们的值设置为null
 * @date 2020-07-03 12:11
 */
@Data
@RequiredArgsConstructor
@Slf4j(topic = "Connect")
public class StopConnect implements ActionListener {
    private final int X = 300;
    private final int Y = 200;

    @NonNull
    private ClientFrame clientFrame;
    @NonNull
    private DefaultInfoPanel defaultInfoPanel;

    private String[][] data = null;
    private final String[] tableInfo = {"文件名","大小","日期","文件类型"};

    @SneakyThrows
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            if((clientFrame.getSocket() != null && !clientFrame.getSocket().isClosed())){
                clientFrame.getSocket().close();
                clientFrame.setSocket(null);
                clientFrame.setDataSocket(null);
                clientFrame.getJPanel3().getJPanel2().getModel().setRowCount(0);
                DefaultTableModel model = new DefaultTableModel(data,tableInfo);
                clientFrame.getJPanel3().getJPanel2().getJTable().setModel(model);
                clientFrame.getJPanel3().getJPanel2().getJTextField().setText("");
                defaultInfoPanel.getJComboBox().setEnabled(true);
                defaultInfoPanel.getJt1().setEditable(true);
                defaultInfoPanel.getJt2().setEditable(true);
                defaultInfoPanel.getJt3().setEditable(true);
                defaultInfoPanel.getJPasswordField().setEditable(true);
                defaultInfoPanel.remove(defaultInfoPanel.getJLabel());
                defaultInfoPanel.add(defaultInfoPanel.getJButton());
                defaultInfoPanel.updateUI();
                log.info("连接状态：" + clientFrame.getDataSocket());
            }else {
                log.info("连接状态：" + false);
                new MessageDialog("提示","请先连接服务器！",clientFrame).init();
            }
        }catch (NullPointerException e1){
            //TODO something
        }
    }
}
