package client.socket;

import client.gui.ClientFrame;
import client.gui.panel.DefaultInfoPanel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author LvHao
 * @Description : 判断命令端口是否是连接状态的线程
 * @date 2020-07-05 3:19
 */
@Data
@AllArgsConstructor
public class IsConnect implements Runnable{

    private ClientFrame clientFrame;

    @Override
    public void run() {
        while (!clientFrame.getSocket().isConnected()){
            System.out.println(clientFrame.getSocket().isConnected());
            DefaultInfoPanel defaultInfoPanel = clientFrame.getJPanel2();
            defaultInfoPanel.getJComboBox().setEnabled(true);
            defaultInfoPanel.getJt1().setEditable(true);
            defaultInfoPanel.getJt2().setEditable(true);
            defaultInfoPanel.getJt3().setEditable(true);
            defaultInfoPanel.getJPasswordField().setEditable(true);
            defaultInfoPanel.remove(defaultInfoPanel.getJLabel());
            defaultInfoPanel.add(defaultInfoPanel.getJButton());
            defaultInfoPanel.updateUI();
        }
    }
}
