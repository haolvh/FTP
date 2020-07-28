package client.gui.panel;

import client.gui.ClientFrame;
import client.gui.MyGridBagConstraints;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.awt.*;

/**
 * @author LvHao
 * @Description :文件面板的分离  这里又分为了两块
 * @date 2020-07-03 1:59
 */
@Data
public class CenterPanel extends JPanel {

    private LocalFilePanel jPanel1;
    private ServerFilePanel jPanel2;

    public CenterPanel(ClientFrame clientFrame){
        init(clientFrame);
    }

    private void init(ClientFrame clientFrame){

        //设置布局方式
        setLayout(new GridBagLayout());

        jPanel1 = new LocalFilePanel(clientFrame);
        add(jPanel1,new MyGridBagConstraints(0,0,1,1).init2());
        jPanel2 = new ServerFilePanel(clientFrame);
        add(jPanel2,new MyGridBagConstraints(1,0,1,1).init2());
    }
}
