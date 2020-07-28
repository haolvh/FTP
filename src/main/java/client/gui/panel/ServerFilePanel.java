package client.gui.panel;

import client.gui.ClientFrame;
import client.gui.MyGridBagConstraints;
import client.gui.action.*;
import client.gui.table.LocalFileTable;
import lombok.Data;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * @author LvHao
 * @Description : 远程文件的遍历
 * @date 2020-07-03 2:09
 */
@Data
public class ServerFilePanel extends JPanel {

    private JLabel jLabel;
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JButton jButton4;
    private JTextField jTextField;
    private JScrollPane jScrollPane;
    private String[] tableInfo = {"文件名","大小","日期","文件类型"};
    private String[][] data;
    private JTable jTable;
    private DefaultTableModel model;

    public ServerFilePanel(ClientFrame clientFrame){
        init(clientFrame);
    }

    private void init(ClientFrame clientFrame){
        setLayout(new GridBagLayout());

        jLabel = new JLabel("远程文件",JLabel.CENTER);
        jButton1 = new JButton("下载 ");
        jButton1.addActionListener(new DownloadFile(this,clientFrame));
        jButton2 = new JButton("删除 ");
        jButton2.addActionListener(new DelServerFile(this,clientFrame));
        jButton3 = new JButton("返回");
        jButton3.addActionListener(new ReturnServerDir(clientFrame,this));
        jButton4 = new JButton("获取远程目录");
        jButton4.addActionListener(new GetServerDir(clientFrame));
        jTextField = new JTextField("远程文件目录",10);
        jTextField.setEditable(false);
        model=new DefaultTableModel(data, tableInfo);
        jTable= new LocalFileTable(model);
        jTable.addMouseListener(new FindServerDir(this,clientFrame));
        jScrollPane = new JScrollPane(jTable);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());


        jPanel.add(jButton1,new MyGridBagConstraints(0,0,1,1).init2());
        jPanel.add(jButton2,new MyGridBagConstraints(1,0,1,1).init2());
        jPanel.add(jButton3,new MyGridBagConstraints(2,0,1,1).init2());
        jPanel.add(jButton4,new MyGridBagConstraints(3,0,1,1).init2());

        add(jLabel,new MyGridBagConstraints(0,0,1,1).init1());

        add(jPanel,new MyGridBagConstraints(0,1,1,1).init1());

        add(jScrollPane,new MyGridBagConstraints(0,2,10,10).init2());

        add(jTextField,new MyGridBagConstraints(0,3,1,1).init1());
    }
}

