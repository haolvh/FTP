package client.gui.action;

import client.gui.panel.LocalFilePanel;
import client.util.GetFiles;
import client.util.OSinfo;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @author LvHao
 * @Description : 表格数据的刷新
 * @date 2020-07-03 2:07
 */
@Data
@RequiredArgsConstructor
public class Flush implements ActionListener {

    private String[][] data = null;
    private final String[] tableInfo = {"文件名","大小","日期","文件类型"};

    @NonNull
    private LocalFilePanel localFilePanel;
    @NonNull
    private DefaultTableModel model;

    @Override
    public void actionPerformed(ActionEvent e) {
        model.setRowCount(0);
        data = GetFiles.getFiles(localFilePanel.getJComboBox());
        model = new DefaultTableModel(data,tableInfo);
        localFilePanel.getJTable().setModel(model);
        if(OSinfo.getOS() == OSinfo.OS.LINUX){
            localFilePanel.getJTextField().setText(String.valueOf(localFilePanel.getJComboBox().getSelectedItem()+File.separator));
        }else{
            localFilePanel.getJTextField().setText(String.valueOf(localFilePanel.getJComboBox().getSelectedItem()));
        }
    }
}
