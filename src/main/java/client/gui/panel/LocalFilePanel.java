package client.gui.panel;

import client.gui.ClientFrame;
import client.gui.action.*;
import client.gui.MyGridBagConstraints;
import client.gui.table.LocalFileTable;
import client.util.GetFiles;
import client.util.OSinfo;
import lombok.Data;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;

/**
 * @author LvHao
 * @Description : 本地文件遍历的实现
 * @date 2020-07-03 2:01
 */
@Data
public class LocalFilePanel extends JPanel {

    private JLabel jLabel = new JLabel("本地文件",JLabel.CENTER);
    private JButton jButton1 = new JButton("   上传    ");
    private JButton jButton3 = new JButton("   返回   ");
    private JButton jButton4 = new JButton("   刷新   ");
    private JTextField jTextField = new JTextField(" ",10);
    private JComboBox jComboBox;
    private JTable jTable;
    private final String[] tableInfo = {"文件名","大小","日期","文件类型"};
    private String[][] data = null;
    private File[] roots = null;

    public LocalFilePanel(ClientFrame clientFrame){
        init(clientFrame);
    }

    private void init(ClientFrame clientFrame){
        setLayout(new GridBagLayout());

        jTextField.setEditable(false);

        //判断系统是window还是linux
        //如果是windows则列出所有的盘符
        //如果是linux则列出‘/’下的所谓文件
        jComboBox = new JComboBox();
        if(OSinfo.getOS() == OSinfo.OS.LINUX){
            roots = new File("/").listFiles();
        }else{
            roots = File.listRoots();
        }
        for(int i = 0;i < roots.length;i++){
            jComboBox.addItem(roots[i]);
        }
        if (OSinfo.getOS() == OSinfo.OS.LINUX) {
            jTextField.setText(String.valueOf(jComboBox.getSelectedItem()+File.separator));
        }else{
            jTextField.setText(String.valueOf(jComboBox.getSelectedItem()));
        }
        data = GetFiles.getFiles(jComboBox);
        DefaultTableModel model=new DefaultTableModel(data, tableInfo);
        jTable = new LocalFileTable(model);
        jTable.addMouseListener(new MouseClickedTwiceListener(this,model));
        JScrollPane jScrollPane = new JScrollPane(jTable);
        jComboBox.addItemListener(new LocalFileChange(this,model));
        jButton1.addActionListener(new FileUpload(this,clientFrame));
        jButton3.addActionListener(new Return(this,model));
        jButton4.addActionListener(new Flush(this,model));

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());

        jPanel.add(jButton1,new MyGridBagConstraints(0,0,1,1).init2());
        jPanel.add(jButton3,new MyGridBagConstraints(1,0,1,1).init2());
        jPanel.add(jButton4,new MyGridBagConstraints(2,0,1,1).init2());
        jPanel.add(jComboBox,new MyGridBagConstraints(3,0,1,1).init2());

        add(jLabel,new MyGridBagConstraints(0,0,1,1).init1());

        add(jPanel,new MyGridBagConstraints(0,1,1,1).init1());

        add(jScrollPane,new MyGridBagConstraints(0,2,10,10).init2());

        add(jTextField,new MyGridBagConstraints(0,3,1,1).init1());
    }
}

